package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.user.client.rpc.core.java.util.Arrays;

@SuppressWarnings("serial")
public class RealVarDTO_Detailed extends VarDTO_Detailed{
	private double moda,mediana,average,dispersion,root_dispersion;
	private ArrayList<Double> filtered_cortage;
	public double getModa() {
		return moda;
	}
	public void setModa(double moda) {
		this.moda = moda;
	}
	public double getMediana() {
		return mediana;
	}
	public void setMediana(double mediana) {
		this.mediana = mediana;
	}
	public double getAverage() {
		return average;
	}
	public void setAverage(double average) {
		this.average = average;
	}
	public double getDispersion() {
		return dispersion;
	}
	public void setDispersion(double dispersion) {
		this.dispersion = dispersion;
	}
	public double getRoot_dispersion() {
		return root_dispersion;
	}
	public void setRoot_dispersion(double root_dispersion) {
		this.root_dispersion = root_dispersion;
	}
	public void calc_statstics()
	{
		double [] ar = new double[filtered_cortage.size()];
		int i = 0;
//		for(Integer index: super.getFiltered_indecies())
//		{
//			ar[i] = super.getDistribution().get(index);
//			i++;
//		}
		for(Double val:filtered_cortage)
		{
			ar[i] = val;
			i++;
		}
		
		java.util.Arrays.sort(ar);
		//calc median
		if(ar.length % 2 == 0 && ar.length > 0) mediana = (ar[ar.length/2]+ar[ar.length/2 - 1])/2;
		else
		{
			if(ar.length % 2 == 1 && ar.length > 0) mediana = ar[ar.length/2];
		}
		//calc moda, average, dispersion
		ArrayList<Double> values = new ArrayList<Double>();
		ArrayList<Integer> values_counts = new ArrayList<Integer>();
		double sum = 0,sum_step2 = 0;
		for(int j = 0; j < ar.length;j++)
		{
			sum+=ar[j];
			sum_step2+=ar[j]*ar[j];
			if(!values.contains(ar[j]))
			{
				values.add(ar[j]);
				values_counts.add(1);
			}else
			{
				int index = values.indexOf(ar[j]);
				values_counts.set(index, values_counts.get(index)+1);
			}
		}
		int max = 0,max_index = 0;
		int index = 0;
		for(Integer count:values_counts)
		{
			if (count > max) {
				max = count;
				max_index = index;
			}
			index++;
		}
		moda = values.get(max_index);
		average = sum / ar.length;
		dispersion = sum_step2/ar.length - average*average;
		root_dispersion = Math.sqrt(dispersion);
	}
	public ArrayList<Double> getFiltered_cortage() {
		return filtered_cortage;
	}
	public void setFiltered_cortage(ArrayList<Double> filtered_cortage) {
		this.filtered_cortage = filtered_cortage;
	}
	public RealVarDTO_Detailed()
	{
		super();	
	}
		
}
