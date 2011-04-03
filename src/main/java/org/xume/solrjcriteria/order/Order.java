/**
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.xume.solrjcriteria.order;

/**
 * @author Johan Siebens
 */
public class Order {

	public static Order asc(String propertyName) {
		return new Order(propertyName, true);
	}

	public static Order desc(String propertyName) {
		return new Order(propertyName, false);
	}

	private boolean ascending;

	private String field;

	private Order(String field, boolean ascending) {
		this.field = field;
		this.ascending = ascending;
	}

	public String getField() {
		return field;
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	public void setField(String field) {
		this.field = field;
	}

}
