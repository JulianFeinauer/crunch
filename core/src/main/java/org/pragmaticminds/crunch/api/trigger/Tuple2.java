/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.pragmaticminds.crunch.api.trigger;


import java.io.Serializable;
import java.util.Optional;

/**
 * Copied from org.apache.flink.api.java.tuple.Tuple2
 *
 * A tuple with 2 fields. Tuples are strongly typed; each field may be of a separate type.
 * The fields of the tuple can be accessed directly as public fields (f0, f1, ...) or via their position
 * through the {@link #getField(int)} method. The tuple field positions start at zero.
 *
 * <p>Tuples are mutable types, meaning that their fields can be re-assigned. This allows functions that work
 * with Tuples to reuse objects in order to reduce pressure on the garbage collector.</p>
 *
 * <p>Warning: If you subclass Tuple2, then be sure to either <ul>
 *  <li> not add any new fields, or </li>
 *  <li> make it a POJO, and always declare the element type of your DataStreams/DataSets to your descendant
 *       type. (That is, if you have a "class Foo extends Tuple2", then don't use instances of
 *       Foo in a DataStream&lt;Tuple2&gt; / DataSet&lt;Tuple2&gt;, but declare it as
 *       DataStream&lt;Foo&gt; / DataSet&lt;Foo&gt;.) </li>
 * </ul></p>
 *
 * @param <T0> The type of field 0
 * @param <T1> The type of field 1
 */
public class Tuple2<T0 extends Serializable, T1 extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Field 0 of the tuple. */
    private T0 f0;
    /** Field 1 of the tuple. */
    private T1 f1;

    /**
     * Creates a new tuple where all fields are null.
     */
    public Tuple2() { /* nothing to do*/ }

    /**
     * Creates a new tuple and assigns the given values to the tuple's fields.
     *
     * @param value0 The value for field 0
     * @param value1 The value for field 1
     */
    public Tuple2(T0 value0, T1 value1) {
        this.f0 = value0;
        this.f1 = value1;
    }

    public T0 getF0() {
        return f0;
    }
    public Optional<T0> getOptionalF0(){
        return Optional.ofNullable(f0);
    }
    public void setF0(T0 f0) {
        this.f0 = f0;
    }
    public T1 getF1() {
        return f1;
    }
    public Optional<T1> getOptionalF1(){
        return Optional.ofNullable(f1);
    }
    public void setF1(T1 f1) {
        this.f1 = f1;
    }

    @SuppressWarnings("squid:S3400") // comes as it is from the lib
    public int getArity() { return 2; }

    @SuppressWarnings("unchecked")
    public <T> T getField(int pos) {
        switch(pos) {
            case 0: return (T) this.f0;
            case 1: return (T) this.f1;
            default: throw new IndexOutOfBoundsException(String.valueOf(pos));
        }
    }

    @SuppressWarnings("unchecked")
    public <T> void setField(T value, int pos) {
        switch(pos) {
            case 0:
                this.f0 = (T0) value;
                break;
            case 1:
                this.f1 = (T1) value;
                break;
            default: throw new IndexOutOfBoundsException(String.valueOf(pos));
        }
    }

    /**
     * Sets new values to all fields of the tuple.
     *
     * @param value0 The value for field 0
     * @param value1 The value for field 1
     */
    public void setFields(T0 value0, T1 value1) {
        this.f0 = value0;
        this.f1 = value1;
    }

    /**
     * Returns a shallow copy of the tuple with swapped values.
     *
     * @return shallow copy of the tuple with swapped values
     */
    public Tuple2<T1, T0> swap() {
        return new Tuple2<>(f1, f0);
    }

    // -------------------------------------------------------------------------------------------------
    // standard utilities
    // -------------------------------------------------------------------------------------------------

    /**
     * Creates a string representation of the tuple in the form
     * (f0, f1),
     * where the individual fields are the value returned by calling {@link Object#toString} on that field.
     * @return The string representation of the tuple.
     */
    @Override
    public String toString() {
        return "(" + this.f0.toString()
                + "," + this.f1.toString()
                + ")";
    }

    /**
     * Deep equality for tuples by calling equals() on the tuple members
     * @param o the object checked for equality
     * @return true if this is equal to o.
     */
    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if (!(o instanceof Tuple2)) {
            return false;
        }
        @SuppressWarnings("rawtypes")
        Tuple2 tuple = (Tuple2) o;
        if (f0 != null ? !f0.equals(tuple.f0) : tuple.f0 != null) {
            return false;
        }
        return f1 != null ? f1.equals(tuple.f1) : tuple.f1 == null;
    }

    @Override
    public int hashCode() {
        int result = f0 != null ? f0.hashCode() : 0;
        result = 31 * result + (f1 != null ? f1.hashCode() : 0);
        return result;
    }

    /**
     * Shallow tuple copy.
     * @return A new Tuple with the same fields as this.
     */
    @SuppressWarnings("unchecked")
    public Tuple2<T0,T1> copy(){
        return new Tuple2<>(this.f0, this.f1);
    }

    /**
     * Creates a new tuple and assigns the given values to the tuple's fields.
     * This is more convenient than using the constructor, because the compiler can
     * infer the generic type arguments implicitly. For example:
     * {@code Tuple3.of(n, x, s)}
     * instead of
     * {@code new Tuple3<Integer, Double, String>(n, x, s)}
     */
    public static <T0 extends Serializable,T1 extends Serializable> Tuple2<T0,T1> of(T0 value0, T1 value1) {
        return new Tuple2<>(value0, value1);
    }
}
