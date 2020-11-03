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

package org.homedns.mkh.dbservice.spi;

import java.io.Reader;
import java.nio.file.Path;

/**
 * It gives access to the application settings and resources
 * 
 */
public interface DBContext {
	
	/**
	 * Returns reader for specified resource
	 * 
	 * @param sName the resource name
	 * 
	 * @return the reader
	 */
	public Reader getResourceReader( String sName );
	
	/**
	 * Returns connections pool parameters file path
	 * 
	 * @return the connections pool parameters file path
	 */
	public Path getCPParamsPath( );
	
	/**
	 * Sets connections pool parameters file path
	 * 
	 * @param path the connections pool parameters file path to set
	 */
	public default void setCPParamsPath( Path path ) {
	}
	
	/**
	 * Returns language code
	 * 
	 * @return the language code
	 */
	public default String getLangCode( ) {
		return( null );
	}
	
	/**
	 * Sets language code
	 * 
	 * @param sLangCode the language code
	 */
	public default void setLangCode( String sLangCode ) {
	}
}