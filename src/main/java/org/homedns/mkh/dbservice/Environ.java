/* 
 * Copyright 2020 Mikhail Khodonov.
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

import java.io.Reader;
import java.util.Locale;
import org.homedns.mkh.databuffer.DBTransaction;
import org.homedns.mkh.databuffer.Environment;
import org.homedns.mkh.dbservice.spi.DBContext;

/**
 * @see org.homedns.mkh.databuffer.Environment implementation 
 * 
 * @author Mikhail Khodonov
 *
 */
public class Environ implements Environment {
	private Locale locale;
	private DBTransaction transObj;
	private DBContext context;
	
	/**
	 * @param app the application context
	 * @param transObj the transaction object
	 */
	public Environ( DBContext context, DBTransaction transObj ) {
		this.context = context;
		this.transObj = transObj;
		setLocale( context.getLangCode( ) );
	}

	/**
	 * @see org.homedns.mkh.databuffer.Environment#getTransObject()
	 */
	@Override
	public DBTransaction getTransObject( ) {
		return( transObj );
	}

	/**
	 * @see org.homedns.mkh.databuffer.Environment#getResourceReader(java.lang.String)
	 */
	@Override
	public Reader getResourceReader( String sName ) {
		if( locale != null ) {
			sName = sName + "_" + locale.getLanguage( );
		}
		return( context.getResourceReader( sName + ".dbuf" ) );
	}

	/**
	 * Sets locale object
	 * 
	 * @param sLangCode the ISO 639 alpha-2 or alpha-3 language code 
	 */
	public void setLocale( String sLangCode ) {
		if( sLangCode == null ) {
			return;
		}
		locale = new Locale( sLangCode );
	}
	
	/**
	 * @see org.homedns.mkh.databuffer.Environment#getLocale()
	 */
	@Override
	public Locale getLocale( ) {
		return( ( locale == null ) ? Environment.super.getLocale( ) : locale );
	}
}
