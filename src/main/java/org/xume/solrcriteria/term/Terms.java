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

package org.xume.solrcriteria.term;

/**
 * @author Johan Siebens
 */
public class Terms {

	public static Term word(String value) {
		return new Word(value);
	}

	public static Term phrase(String value) {
		return new Phrase(value);
	}

	public static Term fielded(String field, String value) {
		return fielded(field, word(value));
	}

	public static Term fielded(final String field, final Term term) {
		return new Term() {

			@Override
			public String value() {
				return field + ":(" + term.value() + ")";
			}

		};
	}

}
