package web;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import DEX.DexAlternative;
import DEX.DexAlternatives;
import DEX.DexAttribute;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexValue;
import DEX.DexViewSettings;

public class EvaluationView {

	static DexProjectEditor lEditor = new DexProjectEditor(null);
	static DexModel lModel = new DexModel(null);
	static DexModelEditor lModEditor = new DexModelEditor(null);
	static DexProject lProject = new DexProject(null);
	static DexViewSettings lSettings = new DexViewSettings(true);
	static DexModelTreeView lModTreeView = new DexModelTreeView(null);
	
	public static void main(String[] args) {
		try {
			lEditor.LoadProject("demo.dxp");
			lProject = lEditor.getProject();
			lModel = lProject.getModels()[1];
			
			System.out.println("Model Name: " + lModel.getName() + "\n");
			
			lModTreeView.setModel(lModel);
					
			lModEditor.BeginEditing();
			
			try {
				lModEditor.EditModel(lModel);
				lModEditor.ViewToJson(lModTreeView, lSettings);
				String lModTreeViewString = lModTreeView.ToJsonString(lSettings);
				JSONObject lModTreeViewJSON = (JSONObject) JSONValue.parse(lModTreeViewString);
				JSONArray lModTreeViewJSONDexObject = (JSONArray) lModTreeViewJSON.get("DexObject");
				JSONObject lModTreeViewJSONView = (JSONObject)((JSONObject) lModTreeViewJSONDexObject.get(0)).get("View");
				JSONObject lModTreeViewJSONRoot = (JSONObject) lModTreeViewJSONView.get("Root");			
				
				DexAttribute lAttCar = (DexAttribute) lModEditor.RefToObject("Att_CAR");
				DexAlternatives lAlternative = lModel.getAlternatives();
				DexAlternative[] allAlternative = lAlternative.getAlternatives();
				for (DexAlternative dexAlternative : allAlternative) {
					System.out.println(dexAlternative.getName());
					DexValue[] values = dexAlternative.getValues();
					for (DexValue dexValue : values) {
						System.out.println(dexValue.getAsString());
					}

				}
//				System.out.println(lAttCar);
				
			}catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}
}
