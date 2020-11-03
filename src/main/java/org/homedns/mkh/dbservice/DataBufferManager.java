/* 
 * Copyright 2018 Mikhail Khodonov.
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
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.dbcp2.BasicDataSource;
import org.homedns.mkh.databuffer.DBTransaction;
import org.homedns.mkh.databuffer.DataBuffer;
import org.homedns.mkh.databuffer.DataBufferMetaData;
import org.homedns.mkh.databuffer.SessionParameters;
import org.homedns.mkh.dbservice.spi.DBContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

/**
 * Data buffer manager implementation
 *
 */
public class DataBufferManager {
	private ConcurrentHashMap< String, DBTransaction > transObjMap;
	private DBContext context;
	
	private DataBufferManager( ) { 
		transObjMap = new ConcurrentHashMap< >( );
	}

	/**
	 * Returns data buffer manager instance
	 * 
	 * @return the data buffer manager
	 */
	public static DataBufferManager getInstance( ) {
		return( Singleton.INSTANCE );
	}
	
	/**
	 * Inits data buffer manager
	 * 
	 * @param context the application context
	 * 
	 * @throws Exception 
	 */
	public void init( DBContext context ) throws Exception {
		this.context = context;
		Path path = context.getCPParamsPath( );
		List< CPParams > list = load( path );
		for( CPParams cpParams : list ) {
			createTransObj( cpParams );
		}
	}
	
	/**
	 * Creates transaction object 
	 * 
	 * @param cpParams the connection pool parameters object
	 * 
	 * @throws Exception
	 */
	private void createTransObj( CPParams cpParams ) throws Exception {
		BasicDataSource ds = new BasicDataSource( );
        ds.setDriverClassName( cpParams.getDriverClassName( ) );
        ds.setUrl( cpParams.getUrl( ) );
        ds.setUsername( cpParams.getUserName( ) );
        ds.setPassword( cpParams.getPassword( ) );
        ds.setRemoveAbandonedOnBorrow( cpParams.isRemoveAbandoned( ) );
        ds.setMaxIdle( cpParams.getMaxIdle( ) );
        ds.setMaxWaitMillis( cpParams.getMaxWaitMillis( ) );
        ds.setMinIdle( cpParams.getMinIdle( ) );
        ds.setMaxTotal( cpParams.getMaxActive( ) );
		ds.setTestWhileIdle( cpParams.isTestWhileIdle( ) );
		transObjMap.put( cpParams.getName( ), new DBTransaction( ds ) );
	}
	
	/**
	 * Returns data buffer with default transaction object
	 * 
	 * @param sClassname the target data buffer class name
	 * 
	 * @return the data buffer
	 * 
	 * @throws Exception
	 */
	public DataBuffer getDataBuffer( String sClassname ) throws Exception {
		return( 
			new DataBuffer( 
				new DataBufferMetaData( 
					sClassname, new Environ( context, transObjMap.get( "default" ) ) 
				) 
			) 
		);
	}
	
	/**
	 * Sets session parameters
	 * 
	 * @param params the session parameters
	 */
	public void setSessionParameters( SessionParameters params ) {
		transObjMap.get( "default" ).setSessionParameters( params );
	}
	
	/**
	 * Returns data buffer with specified transaction object
	 * 
	 * @param sClassname the target data buffer class name
	 * @param sTransObjName the transaction object name
	 * 
	 * @return the data buffer
	 * 
	 * @throws Exception
	 */
	public DataBuffer getDataBuffer( String sClassname, String sTransObjName ) throws Exception {
		return( 
			new DataBuffer( 
				new DataBufferMetaData( 
					sClassname, new Environ( context, transObjMap.get( sTransObjName ) ) 
				) 
			) 
		);		
	}
	
	/**
	 * Loads connection pool parameters
	 * 
	 * @param path the path to the connection pool parameters file
	 * 
	 * @return the connection pool parameters
	 * 
	 * @throws IOException
	 */
	private List< CPParams > load( Path path ) throws IOException  {
		List< CPParams > list = new ArrayList< >( );
		try( JsonReader in = new JsonReader( new FileReader( path.toFile( ) ) ) ) {
			Type type = new TypeToken< List< CPParams > >( ){ }.getType( );
			list = new Gson( ).fromJson( in, type );
		}
		return( list );
	}
	
	/**
	 * Inner class to provide instance of the class
	 */
	private static class Singleton {
		private static final DataBufferManager INSTANCE = new DataBufferManager( );		
	}
}