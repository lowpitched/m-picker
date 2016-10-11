package com.mlh.model.pick;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author menglihao
 * @since 20140508
 * @version 1.0
 */
public class PickRow {

	private String id;
	
	private String foreignId;
	
	private List<PickUnit> units;
	
	public PickRow(String id,String foreignId,List<PickUnit> infos){
		this.id=id;
		this.foreignId=foreignId;
		this.units=infos;
	}
	
	public boolean addUnit(PickUnit unit){
		return units.add(unit);
	}
	
	public List<PickUnit> getUnits() {
		return units;
	}

	public PickUnit removeUnit(int index){
		return units.remove(index);
	}
	
	public boolean removeUnit(PickUnit unit){
		return units.remove(unit);
	}
	
	public PickUnit getUnit(int index){
		return units.get(index);
	}
	
	public void addAll(List<PickUnit> units){
		this.units.addAll(units);
	}
	
	public int getUnitSize(){
		return units.size();
	}
	
	public String getId() {
		return id;
	}
	
	public String getForeignId() {
		return foreignId;
	}

	public List<PickUnit> toList(){
		 List<PickUnit> result = new ArrayList<PickUnit>();
		 result.addAll(units);
		 if(null!=this.id&&!"".equals(this.id)){
			 result.add(0,new PickUnit(this.id));
		 }
		 if(null!=foreignId&&!"".equals(this.foreignId)){
			 result.add(1,new PickUnit(this.foreignId));
		 }
		 return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[");
		for(int i=0;i<units.size()-1;i++){
			builder.append(units.get(i)).append(",");
		}
		builder.append(units.get(units.size()-1)).append("]");
		return "PickInfo [id=" + id + ", foreignId=" + foreignId + ", infos="
				+ builder.toString()
				+ "]";
	}
	
	
	
}
