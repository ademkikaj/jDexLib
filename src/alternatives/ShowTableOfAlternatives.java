package alternatives;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import DEX.DexAlternative;
import DEX.DexAlternatives;
import DEX.DexAttribute;
import DEX.DexCategoricalScale;
import DEX.DexCategory;
import DEX.DexDataCell;
import DEX.DexDataHeader;
import DEX.DexIntInterval;
import DEX.DexIntSingle;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexScaleEditor;
import DEX.DexScaleStrings;
import DEX.DexValue;
import DEX.DexViewSettings;
import Utils.Values;

public class ShowTableOfAlternatives {

	static DexProjectEditor lProjectEditor;
	static DexProject lProject;
	static DexModelEditor lModelEditor;
	static DexModel lModel;
	static DexModelTreeView lModelTreeView;
	static DexViewSettings lSettings;
	static DexScaleEditor lScaleEditor;
	
	public static void main(String[] args) {
		lProjectEditor = new DexProjectEditor(null);
		lProjectEditor.LoadProject("demo.dxp");
		lProject = lProjectEditor.getProject();
		lModel = lProject.getModels()[1];
				
//		System.out.println(showAlternatives(lModel));
//		
//		getButtonState(lModel, 0);
		

		setValueToAlternative(lModel, 3, 0, "2");
		getValueOfAlternative(lModel, 3, 0);
		
//		System.out.println(showAlternatives(lModel));
	}
	
	public static JSONObject showAlternatives(DexModel lModel) {
		
		JSONObject res = new JSONObject();
		JSONArray nameOfAlternatives = new JSONArray();
		JSONArray tableOfAlternatives = new JSONArray();
		JSONObject row;
		JSONArray items;
		JSONObject item;
		
		DexAlternatives lAllAlternatives = lModel.getAlternatives();
		
		DexDataHeader lHeaders = lAllAlternatives.getHeader();
		for (int i = 0; i < lHeaders.getHeadCount(); i++) {
			System.err.println(lHeaders.getHead(i).getName());
		}
		
		
		if(lAllAlternatives.getAltCount()>0) {
			DexAlternative[] alternatives = lAllAlternatives.getAlternatives();
			for (DexAlternative dexAlternative : alternatives) {
				nameOfAlternatives.add(dexAlternative.getName());
			}
		}
		
		int altCount = lAllAlternatives.getAltCount();
		ArrayList<DexAttribute> terminals = lModel.getRoot().TerminalAttributes();		
		DexScaleStrings lDSS = new DexScaleStrings(null, null);
		DexDataHeader lAlternativesHeader = lAllAlternatives.getHeader();
		for (DexAttribute dexAttribute : terminals) {
			row = new JSONObject();
			row.put("terminal",dexAttribute.getName());
			if(altCount > 0)
			{
				items = new JSONArray();
				for (int i = 0; i < altCount; i++) {
					item = new JSONObject();
					DexValue v = lAllAlternatives.getValue(i, lAlternativesHeader.HeadIndex(dexAttribute));				
					item.put("position", i);
					item.put("value", lDSS.ValueOnScaleString(v, dexAttribute.getScale()));
					items.add(item);
				}
				row.put("items", items);
			}
			tableOfAlternatives.add(row);
		}
		res.put("nameOfAlternatives", nameOfAlternatives);
		res.put("tableOfAlternatives", tableOfAlternatives);
		return res;
	}

	public static void getValueOfAlternative(DexModel lModel, int row, int col) {
		JSONArray JCell;
		JSONObject value;
		JSONObject selected;
		JSONArray values = new JSONArray();
		DexAlternatives lAllAlternatives = lModel.getAlternatives();
		DexAlternative lAlt = lAllAlternatives.getAlternative(col);
		ArrayList<DexAttribute> terminals = lModel.getRoot().TerminalAttributes();	
		
		DexDataCell cell = lAlt.getEntry(terminals.get(row));
		DexScaleStrings lDSS = new DexScaleStrings(null, null);
		
		String actualValue = lDSS.ValueOnScaleString(cell.getValue(), terminals.get(row).getScale());
		
		DexAttribute lAtt = terminals.get(row);
		DexCategoricalScale lAttScale = (DexCategoricalScale) lAtt.getScale();
		DexCategory[] lScaleCategories = lAttScale.getCategories();

		for (DexCategory dexCategory : lScaleCategories) {
			JCell = new JSONArray();
			value = new JSONObject();
			selected = new JSONObject();
			selected.put("selected", (dexCategory.getName().equals(actualValue) ? true : false));
			value.put("value", dexCategory.getName());
			JCell.add(selected);
			JCell.add(value);
			values.add(JCell);
		}
		JCell = new JSONArray();
		value = new JSONObject();
		selected = new JSONObject();
		selected.put("selected", ("*".equals(actualValue) ? true : false));
		value.put("value", "*");
		JCell.add(selected);
		JCell.add(value);
		values.add(JCell);
		System.out.println(values);
	}
	
	public static void setValueToAlternative(DexModel lModel, int row, int col, String value) {
		DexAlternatives lAllAlternatives = lModel.getAlternatives();
		DexAlternative lAlt = lAllAlternatives.getAlternative(col);
		ArrayList<DexAttribute> terminals = lModel.getRoot().TerminalAttributes();			
		DexDataCell cell = lAlt.getEntry(terminals.get(row));
		DexAttribute lAtt = terminals.get(row);
		DexCategoricalScale lAttScale = (DexCategoricalScale) lAtt.getScale();		
		if(value.equals("*")) {
			DexIntInterval v = new DexIntInterval();
			v.setValue(Values.IntInt(lAttScale.getLowIntBound(), lAttScale.getHighIntBound()));
			cell.setValue(v);
		}else {
			DexIntSingle v = new DexIntSingle();
			v.setValue(Values.IntSingle(Integer.parseInt(value)));
			cell.setValue(v);
		}	
	}

	
	public static void getButtonState(DexModel lModel, int ida) {
		DexAlternatives lAllAlternatives = lModel.getAlternatives();		
		System.err.println("Add Alternative: " + (lAllAlternatives.CanAddAlternative() ? true : false));
		System.err.println("Delete Alternative: " + (lAllAlternatives.CanDeleteAlternative(ida) ? true : false));
		System.err.println("Left Alternative: " + (lAllAlternatives.CanMoveAlternativePrior(ida) ? true : false));
		System.err.println("Right Alternative: " + (lAllAlternatives.CanMoveAlternativeNext(ida) ? true : false));
		System.err.println("Cut Alternative: " + (ida>-1 ? true : false));
		System.err.println("Copy Alternative: " + (ida>-1 ? true : false));
		System.err.println("Duplicate Alternative: " + (ida>-1 ? (lAllAlternatives.CanDuplicateAlternative(lAllAlternatives.getAlternative(ida), ida+1) ? true : false) : false));			
	}
}
