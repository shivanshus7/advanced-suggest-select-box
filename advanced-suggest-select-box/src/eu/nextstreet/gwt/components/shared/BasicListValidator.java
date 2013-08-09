/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.nextstreet.gwt.components.shared;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Zied Hamdi founder of http://1vu.fr
 * 
 * @param <T>
 */
public class BasicListValidator<T> implements Validator<T> {
	protected List<Validator<T>> validators = new ArrayList<Validator<T>>();

	@Override
	public void validate(T value) throws ValidationException {
		for (Validator<T> validator : validators) {
			validateCurrent(value, validator);
		}
	}

	/**
	 * validates one of the validators in the list and gives the opportunity to handle the exception differently
	 * 
	 * @param value
	 * @param validator
	 * @throws ValidationException
	 */
	protected void validateCurrent(T value, Validator<T> validator) throws ValidationException {
		validator.validate(value);
	}

	public void add(int index, Validator<T> element) {
		validators.add(index, element);
	}

	public boolean add(Validator<T> e) {
		return validators.add(e);
	}

	public boolean addAll(Collection<? extends Validator<T>> c) {
		return validators.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends Validator<T>> c) {
		return validators.addAll(index, c);
	}

	public void clear() {
		validators.clear();
	}

	public boolean contains(Object o) {
		return validators.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return validators.containsAll(c);
	}

	public Validator<T> get(int index) {
		return validators.get(index);
	}

	public int indexOf(Object o) {
		return validators.indexOf(o);
	}

	public boolean isEmpty() {
		return validators.isEmpty();
	}

	public Iterator<Validator<T>> iterator() {
		return validators.iterator();
	}

	public Validator<T> remove(int index) {
		return validators.remove(index);
	}

	public boolean remove(Object o) {
		return validators.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return validators.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return validators.retainAll(c);
	}

	public Validator<T> set(int index, Validator<T> element) {
		return validators.set(index, element);
	}

	public int size() {
		return validators.size();
	}

}
