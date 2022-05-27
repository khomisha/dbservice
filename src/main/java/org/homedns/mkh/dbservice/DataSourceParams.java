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

/**
 * Data source parameters object for details @see <a href=
 * "commons.apache.org/proper/commons-dbcp/configuration.html">commons-dbcp2
 * configuration</a>
 *
 */
public class DataSourceParams {
	private String name;
	private String userName;
	private String password;
	private String driverClassName;
	private String url;
	private int maxActive;
	private int maxIdle;
	private int minIdle;
	private long maxWaitMillis;
	private boolean testWhileIdle;
	private boolean removeAbandoned;
	
	public DataSourceParams( ) { }
	
	/**
	 * @return the name
	 */
	public String getName( ) {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName( String name ) {
		this.name = name;
	}

	/**
	 * @return the userName
	 */
	public String getUserName( ) {
		return userName;
	}
	
	/**
	 * @param userName the userName to set
	 */
	public void setUserName( String userName ) {
		this.userName = userName;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword( ) {
		return password;
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword( String password ) {
		this.password = password;
	}
	
	/**
	 * @return the driverClassName
	 */
	public String getDriverClassName( ) {
		return driverClassName;
	}
	
	/**
	 * @param driverClassName the driverClassName to set
	 */
	public void setDriverClassName( String driverClassName ) {
		this.driverClassName = driverClassName;
	}

	/**
	 * @return the url
	 */
	public String getUrl( ) {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl( String url ) {
		this.url = url;
	}

	/**
	 * @return the maxActive
	 */
	public int getMaxActive( ) {
		return maxActive;
	}
	
	/**
	 * @param maxActive the maxActive to set
	 */
	public void setMaxActive( int maxActive ) {
		this.maxActive = maxActive;
	}
	
	/**
	 * @return the maxIdle
	 */
	public int getMaxIdle( ) {
		return maxIdle;
	}
	
	/**
	 * @param maxIdle the maxIdle to set
	 */
	public void setMaxIdle( int maxIdle ) {
		this.maxIdle = maxIdle;
	}
	
	/**
	 * @return the minIdle
	 */
	public int getMinIdle( ) {
		return minIdle;
	}
	
	/**
	 * @param minIdle the minIdle to set
	 */
	public void setMinIdle( int minIdle ) {
		this.minIdle = minIdle;
	}
	
	/**
	 * @return the maxWaitMillis
	 */
	public long getMaxWaitMillis( ) {
		return maxWaitMillis;
	}
	
	/**
	 * @param maxWaitMillis the maxWaitMillis to set
	 */
	public void setMaxWaitMillis( long maxWaitMillis ) {
		this.maxWaitMillis = maxWaitMillis;
	}
	
	/**
	 * @return the testWhileIdle
	 */
	public boolean isTestWhileIdle( ) {
		return testWhileIdle;
	}
	
	/**
	 * @param testWhileIdle the testWhileIdle to set
	 */
	public void setTestWhileIdle( boolean testWhileIdle ) {
		this.testWhileIdle = testWhileIdle;
	}
	
	/**
	 * @return the removeAbandoned
	 */
	public boolean isRemoveAbandoned( ) {
		return removeAbandoned;
	}
	
	/**
	 * @param removeAbandoned the removeAbandoned to set
	 */
	public void setRemoveAbandoned( boolean removeAbandoned ) {
		this.removeAbandoned = removeAbandoned;
	}
}
