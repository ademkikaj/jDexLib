package dexscaleeditor;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import DEX.DexAttribute;
import DEX.DexCategoricalScale;
import DEX.DexCategory;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexScale;
import DEX.DexScaleEditor;
import DEX.DexScaleOrder;
import DEX.DexViewSettings;

public class ReadScale {

	static DexProjectEditor lEditor = new DexProjectEditor(null);
	static DexModel lModel = new DexModel(null);
	static DexModelEditor lModEditor = new DexModelEditor(null);
	static DexProject lProject = new DexProject(null);
	static DexViewSettings lSettings = new DexViewSettings(true);
	static DexModelTreeView lModTreeView = new DexModelTreeView(null);
	static DexScaleEditor lScaleEditor = new DexScaleEditor(null);

	
	public static void main(String[] args) {
		
		lEditor.LoadProject("demo.dxp");
		lProject = lEditor.getProject();
		lModel = lProject.getModels()[1];
		
		lModTreeView.setModel(lModel);

		lModEditor.BeginEditing();

		try {
			lModEditor.EditModel(lModel);
			lModEditor.ViewToJson(lModTreeView, lSettings);

			lModTreeView.ToJsonString(lSettings);
			
			DexAttribute lAttrCar = (DexAttribute) lModEditor.RefToObject("Att_CAR");
			
			DexAttribute lNewAtt = new DexAttribute(null);
			lNewAtt.setName("A");
			lModEditor.AddInputTo(lAttrCar, lNewAtt);
			
			System.err.println(lNewAtt.CanSetScale(new DexCategoricalScale(null)));
			lNewAtt.setScale(new DexCategoricalScale(null));
			System.out.println(lNewAtt.getScale().getCount());
			DexCategoricalScale lCategoricalScale = (DexCategoricalScale) lNewAtt.getScale();
			try {
				lScaleEditor.BeginEditing();
				lScaleEditor.EditScale(lCategoricalScale);
				System.err.println(lScaleEditor.CanAddCategory());
				lScaleEditor.SetScaleOrder(DexScaleOrder.Increasing);
				lScaleEditor.AddCategory("New Value Bad", null);
				lScaleEditor.AddCategory("New Value Good", null);
			} catch (Exception e) {
				System.err.println(e);
			}finally {
				lScaleEditor.EndEditing();
			}
			System.out.println(getDexScaleTableView(lCategoricalScale));
			
			
//			
////			lModEditor.AddInputTo(lAttrCar, new DexAttribute(null));
//			
//			System.out.println(lAttrCar.getName());
//			
//			DexScale lCarScale = lAttrCar.getScale();
//
//			DexCategoricalScale lCatScale = (DexCategoricalScale) lCarScale;
////			lScaleEditor.EditScale(lCarScale);
//			
//			System.out.println(getDexScaleTableView(lCarScale));
//			setDexScaleCategoryOrder("None");
//			System.out.println(getDexScaleTableView(lCarScale));
						
			/*System.out.println("===============");
			System.out.println(getDexScaleTableView(lCarScale));
			System.out.println("===============");
			System.out.println(getDexScaleTableButtonStatus(lCarScale, 0));
			System.out.println(getDexScaleTableButtonStatus(lCarScale, 3));
			System.out.println("===============");
			insertDexScaleCategory(0);
			System.out.println(getDexScaleTableView(lCarScale));
			System.out.println("===============");
			deleteDexScaleCategory(1);
			System.out.println(getDexScaleTableView(lCarScale));
			System.out.println("===============");
			setDexScaleCategoryOrder("None");
			System.out.println(getDexScaleTableView(lCarScale));
			System.out.println("===============");
			setDexScaleCategoryOrder("Increasing");
			System.out.println(getDexScaleTableView(lCarScale));
			System.out.println("===============");
			setDexScaleCategoryOrder("Decreasing");
			System.out.println(getDexScaleTableView(lCarScale));
			System.out.println("===============");
			moveDownDexScaleCategory(0);
			System.out.println(getDexScaleTableView(lCarScale));
			System.out.println("===============");
			moveUpDexScaleCategory(1);
			System.out.println(getDexScaleTableView(lCarScale));
			System.out.println("===============");
			renameDexScaleCategory("renamed", "renameddesc", 0);
			System.out.println(getDexScaleTableView(lCarScale));*/
			
		}
		catch (Exception e) {
			System.err.println(e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	static JSONArray getDexScaleTableView(DexScale scale) {
		
		JSONArray allCategories = new JSONArray();		
		// check the type of the scale
		if(scale.getIsCategorical()) {
			// if it is category scale - Like in DEXi, cast it to DexCategoricalScale
			DexCategoricalScale lCatScale = (DexCategoricalScale) scale;
			// get all categories
			DexCategory[] categories = lCatScale.getCategories();
			int categoryCount = lCatScale.getCount();
			
			for (int i = 0; i < categoryCount; i++) {
				JSONObject categoryJSON = new JSONObject();
				DexCategory current = categories[i];
				categoryJSON.put("Position", i);
				if(lCatScale.IsBad(i)) 
					categoryJSON.put("Type", "Bad");				
				else if(lCatScale.IsNeutral(i)) 
					categoryJSON.put("Type", "Neutral");
				else if(lCatScale.IsGood(i))
					categoryJSON.put("Type", "Good");
				else
					categoryJSON.put("Type", "Neutral");
				categoryJSON.put("Name", current.getName());
				categoryJSON.put("Description", current.getDescription());
				allCategories.add(categoryJSON);
			}			
		}else
			System.err.println("This type of scale is not categorical.");
		return allCategories;
	}
	
	static JSONObject getDexScaleTableButtonStatus(DexScale scale, int position) {
		HashMap<String, String> buttonsStatus = new HashMap<>();
		if(scale.getIsCategorical()) {
			DexCategoricalScale lCatScale = (DexCategoricalScale) scale;
			buttonsStatus.put("add", (lCatScale.CanInsertCategory(position) ?  "enabled" : "disabled"));
			buttonsStatus.put("delete", (lCatScale.CanDeleteCategory(position) ?  "enabled" : "disabled"));
			buttonsStatus.put("bad", (lCatScale.CanSetBadPoint(position) ?  "enabled" : "disabled"));
			buttonsStatus.put("good", (lCatScale.CanSetGoodPoint(position) ?  "enabled" : "disabled"));
			buttonsStatus.put("moveup", (lCatScale.CanMoveCategoryPrior(position) ?  "enabled" : "disabled"));
			buttonsStatus.put("movedown", (lCatScale.CanMoveCategoryNext(position) ?  "enabled" : "disabled"));
		}
		return new JSONObject(buttonsStatus);
	}

	static void insertDexScaleCategory(int position) {
		lScaleEditor.InsertCategory(position+1, "New Value", null);
	}
	
	static void deleteDexScaleCategory(int position) {
		lScaleEditor.DeleteCategory(position);
	}

	static void setBadPoint(int position) {
		lScaleEditor.SetBadPoint(position);
	}
	
	static void setGoodPoint(int position) {
		lScaleEditor.SetGoodPoint(position);
	}
	
	static void setDexScaleCategoryOrder(String type) {
		lScaleEditor.SetScaleOrder(DexScaleOrder.valueOf(type));
	}
	
	static void moveUpDexScaleCategory(int position) {
		lScaleEditor.MoveCategoryPrior(position);
	}
	
	static void moveDownDexScaleCategory(int position) {
		lScaleEditor.MoveCategoryNext(position);
	}
	
	static void renameDexScaleCategory(String name, String description, int position) {
		lScaleEditor.RenameTo(position, name, description);
	}
}
