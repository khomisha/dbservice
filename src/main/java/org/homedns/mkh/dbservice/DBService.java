/* 
 * Copyright 2022 Mikhail Khodonov.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.homedns.mkh.dbservice;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbcp2.BasicDataSource;
import org.homedns.mkh.databuffer.DBConnection;
import org.homedns.mkh.databuffer.api.SessionParameters;
import org.homedns.mkh.databuffer.api.BaseDataBufferManager;
import org.homedns.mkh.databuffer.api.DBMFactory;
import org.homedns.mkh.databuffer.api.DataBufferManager;
import org.homedns.mkh.databuffer.api.DataSourceWrapper;
import org.homedns.mkh.databuffer.api.GenericDataSource;
import org.homedns.mkh.util.Util;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

/**
 * Abstract database service
 *
 */
public abstract class DBService {
	protected DataBufferManager dbm;
	
	private DBService( ) {
	}
	
	/**
	 * Inits data buffer manager
	 * 
	 * @param context the application context
	 * 
	 * @throws Exception 
	 */
	public void init( Path path ) throws Exception {
		List< DataSourceParams > dspList = load( path );
		List< GenericDataSource > gds = new ArrayList< >( );
		for( DataSourceParams dsp : dspList ) {
			gds.add( getDataSource( dsp ) );
		}
		dbm = DBMFactory.create( BaseDataBufferManager.class, gds );
	}

	/**
	 * Sets session parameters
	 * 
	 * @param params the session parameters
	 */
	public void setSessionParameters( SessionParameters params ) {
		DBConnection dbConn = ( DBConnection )dbm.getDataSource( DataBufferManager.DEFAULT_DATASOURCE_NAME );
		dbConn.setSessionParameters( params );
	}

	/**
	 * Returns generic data source 
	 * 
	 * @param dsp the data source parameters
	 * 
	 * @return the generic data source
	 */
	private GenericDataSource getDataSource( DataSourceParams dsp ) {
		BasicDataSource ds = new BasicDataSource( );
        ds.setDriverClassName( dsp.getDriverClassName( ) );
        ds.setUrl( dsp.getUrl( ) );
        ds.setUsername( dsp.getUserName( ) );
        ds.setPassword( dsp.getPassword( ) );
        ds.setRemoveAbandonedOnBorrow( dsp.isRemoveAbandoned( ) );
        ds.setMaxIdle( dsp.getMaxIdle( ) );
        ds.setMaxWaitMillis( dsp.getMaxWaitMillis( ) );
        ds.setMinIdle( dsp.getMinIdle( ) );
        ds.setMaxTotal( dsp.getMaxActive( ) );
		ds.setTestWhileIdle( dsp.isTestWhileIdle( ) );
		return( new DataSourceWrapper( dsp.getName( ), ds ) );
	}
	
	/**
	 * Loads connection pool parameters
	 * 
	 * @param path the path to the connection pool parameters file
	 * 
	 * @return the connection pool parameters list
	 * 
	 * @throws IOException
	 */
	private List< DataSourceParams > load( Path path ) throws IOException  {
		List< DataSourceParams > list = new ArrayList< >( );
		try( JsonReader in = new JsonReader( new FileReader( path.toFile( ) ) ) ) {
			Type type = new TypeToken< List< DataSourceParams > >( ){ }.getType( );
			list = Util.getGson( ).fromJson( in, type );
		}
		return( list );
	}
}
