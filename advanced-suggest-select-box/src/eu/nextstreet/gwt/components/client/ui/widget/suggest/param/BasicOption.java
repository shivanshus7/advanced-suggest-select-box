/*
 * Copyright 2012 Zied Hamdi.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package eu.nextstreet.gwt.components.client.ui.widget.suggest.param;

/**
 * 
 * @author Zied Hamdi - http://1vu.fr
 * 
 * @param <T>
 *          the type of the option value
 */
public class BasicOption<T> implements Option<T> {
	protected String key;
	protected T value;

	public BasicOption() {
	}

	public BasicOption(String key) {
		this.key = key;
	}

	public BasicOption(String key, T value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicOption other = (BasicOption) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String name = getClass().getName();
		return name.substring(name.lastIndexOf('.')) + "[key=" + key + ", value=" + value + "]";
	}

}
