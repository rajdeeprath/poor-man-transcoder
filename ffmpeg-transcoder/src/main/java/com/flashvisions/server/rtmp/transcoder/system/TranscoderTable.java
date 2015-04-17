package com.flashvisions.server.rtmp.transcoder.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;


public class TranscoderTable extends Hashtable<ITranscoderResource, ArrayList<ITranscoderResource>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -34684041647756780L;

	@Override
	public synchronized void clear() {
		// TODO Auto-generated method stub
		super.clear();
	}

	@Override
	public synchronized Object clone() {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public synchronized ArrayList<ITranscoderResource> compute(
			ITranscoderResource arg0,
			BiFunction<? super ITranscoderResource, ? super ArrayList<ITranscoderResource>, ? extends ArrayList<ITranscoderResource>> arg1) {
		// TODO Auto-generated method stub
		return super.compute(arg0, arg1);
	}

	@Override
	public synchronized ArrayList<ITranscoderResource> computeIfAbsent(
			ITranscoderResource arg0,
			Function<? super ITranscoderResource, ? extends ArrayList<ITranscoderResource>> arg1) {
		// TODO Auto-generated method stub
		return super.computeIfAbsent(arg0, arg1);
	}

	@Override
	public synchronized ArrayList<ITranscoderResource> computeIfPresent(
			ITranscoderResource arg0,
			BiFunction<? super ITranscoderResource, ? super ArrayList<ITranscoderResource>, ? extends ArrayList<ITranscoderResource>> arg1) {
		// TODO Auto-generated method stub
		return super.computeIfPresent(arg0, arg1);
	}

	@Override
	public synchronized boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return super.contains(arg0);
	}

	@Override
	public synchronized boolean containsKey(Object arg0) {
		// TODO Auto-generated method stub
		return super.containsKey(arg0);
	}

	@Override
	public boolean containsValue(Object arg0) {
		// TODO Auto-generated method stub
		return super.containsValue(arg0);
	}

	@Override
	public synchronized Enumeration<ArrayList<ITranscoderResource>> elements() {
		// TODO Auto-generated method stub
		return super.elements();
	}

	@Override
	public Set<java.util.Map.Entry<ITranscoderResource, ArrayList<ITranscoderResource>>> entrySet() {
		// TODO Auto-generated method stub
		return super.entrySet();
	}

	@Override
	public synchronized boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
	}

	@Override
	public synchronized void forEach(
			BiConsumer<? super ITranscoderResource, ? super ArrayList<ITranscoderResource>> arg0) {
		// TODO Auto-generated method stub
		super.forEach(arg0);
	}

	@Override
	public synchronized ArrayList<ITranscoderResource> get(Object arg0) {
		// TODO Auto-generated method stub
		return super.get(arg0);
	}

	@Override
	public synchronized ArrayList<ITranscoderResource> getOrDefault(Object arg0,
			ArrayList<ITranscoderResource> arg1) {
		// TODO Auto-generated method stub
		return super.getOrDefault(arg0, arg1);
	}

	@Override
	public synchronized int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public synchronized boolean isEmpty() {
		// TODO Auto-generated method stub
		return super.isEmpty();
	}

	@Override
	public Set<ITranscoderResource> keySet() {
		// TODO Auto-generated method stub
		return super.keySet();
	}

	@Override
	public synchronized Enumeration<ITranscoderResource> keys() {
		// TODO Auto-generated method stub
		return super.keys();
	}

	@Override
	public synchronized ArrayList<ITranscoderResource> merge(
			ITranscoderResource arg0,
			ArrayList<ITranscoderResource> arg1,
			BiFunction<? super ArrayList<ITranscoderResource>, ? super ArrayList<ITranscoderResource>, ? extends ArrayList<ITranscoderResource>> arg2) {
		// TODO Auto-generated method stub
		return super.merge(arg0, arg1, arg2);
	}

	@Override
	public synchronized ArrayList<ITranscoderResource> put(
			ITranscoderResource arg0, ArrayList<ITranscoderResource> arg1) {
		// TODO Auto-generated method stub
		return super.put(arg0, arg1);
	}

	@Override
	public synchronized void putAll(
			Map<? extends ITranscoderResource, ? extends ArrayList<ITranscoderResource>> arg0) {
		// TODO Auto-generated method stub
		super.putAll(arg0);
	}

	@Override
	public synchronized ArrayList<ITranscoderResource> putIfAbsent(
			ITranscoderResource arg0, ArrayList<ITranscoderResource> arg1) {
		// TODO Auto-generated method stub
		return super.putIfAbsent(arg0, arg1);
	}

	@Override
	protected void rehash() {
		// TODO Auto-generated method stub
		super.rehash();
	}

	@Override
	public synchronized boolean remove(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return super.remove(arg0, arg1);
	}

	@Override
	public synchronized ArrayList<ITranscoderResource> remove(Object arg0) {
		// TODO Auto-generated method stub
		return super.remove(arg0);
	}

	@Override
	public synchronized boolean replace(ITranscoderResource arg0,
			ArrayList<ITranscoderResource> arg1,
			ArrayList<ITranscoderResource> arg2) {
		// TODO Auto-generated method stub
		return super.replace(arg0, arg1, arg2);
	}

	@Override
	public synchronized ArrayList<ITranscoderResource> replace(
			ITranscoderResource arg0, ArrayList<ITranscoderResource> arg1) {
		// TODO Auto-generated method stub
		return super.replace(arg0, arg1);
	}

	@Override
	public synchronized void replaceAll(
			BiFunction<? super ITranscoderResource, ? super ArrayList<ITranscoderResource>, ? extends ArrayList<ITranscoderResource>> arg0) {
		// TODO Auto-generated method stub
		super.replaceAll(arg0);
	}

	@Override
	public synchronized int size() {
		// TODO Auto-generated method stub
		return super.size();
	}

	@Override
	public synchronized String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	public Collection<ArrayList<ITranscoderResource>> values() {
		// TODO Auto-generated method stub
		return super.values();
	}

	
}
