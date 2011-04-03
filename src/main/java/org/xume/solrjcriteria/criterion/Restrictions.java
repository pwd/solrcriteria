/**
 * Copyright 2010 the original author or authors.
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

package org.xume.solrjcriteria.criterion;

import java.util.Arrays;
import java.util.List;

import org.xume.solrjcriteria.value.Value;

/**
 * @author Johan Siebens
 */
public class Restrictions {

	public static Criterion and(Criterion... criterions) {
		return and(Arrays.asList(criterions));
	}

	public static Criterion and(List<Criterion> criterions) {
		return new Conjunction(criterions);
	}

	public static Criterion eq(String field, Value value) {
		return new EqCriterion(field, value);
	}

	public static Criterion eq(Value value) {
		return new EqCriterion(value);
	}

	public static Criterion ge(String field, Value value) {
		return new GeCriterion(field, value);
	}

	public static Criterion ge(Value value) {
		return new GeCriterion(value);
	}

	public static Criterion gt(String field, Value value) {
		return new GtCriterion(field, value);
	}

	public static Criterion gt(Value value) {
		return new GtCriterion(value);
	}

	public static Criterion le(String field, Value value) {
		return new LeCriterion(field, value);
	}

	public static Criterion le(Value value) {
		return new LeCriterion(value);
	}

	public static Criterion lt(String field, Value value) {
		return new LtCriterion(field, value);
	}

	public static Criterion lt(Value value) {
		return new LtCriterion(value);
	}

	public static Criterion ne(String field, Value value) {
		return new NeCriterion(field, value);
	}

	public static Criterion ne(Value value) {
		return new NeCriterion(value);
	}

	public static Criterion or(Criterion... criterions) {
		return or(Arrays.asList(criterions));
	}

	public static Criterion or(List<Criterion> criterions) {
		return new Disjunction(criterions);
	}

}