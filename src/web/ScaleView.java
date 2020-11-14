package web;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import DEX.DexAttribute;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexScale;
import DEX.DexViewSettings;

public class ScaleView {

	static DexProjectEditor lEditor = new DexProjectEditor(null);
	static DexModel lModel = new DexModel(null);
	static DexModelEditor lModEditor = new DexModelEditor(null);
	static DexProject lProject = new DexProject(null);
	static DexViewSettings lSettings = new DexViewSettings(true);
	static DexModelTreeView lModTreeView = new DexModelTreeView(null);
	static ArrayList<DexScale> allScales = null;
	
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
				allScales = lModel.getRoot().AllScales(false);
				String lModTreeViewString = lModTreeView.ToJsonString(lSettings);
				JSONObject lModTreeViewJSON = (JSONObject) JSONValue.parse(lModTreeViewString);
				JSONArray lModTreeViewJSONDexObject = (JSONArray) lModTreeViewJSON.get("DexObject");
				JSONObject lModTreeViewJSONView = (JSONObject)((JSONObject) lModTreeViewJSONDexObject.get(0)).get("View");
				JSONObject lModTreeViewJSONRoot = (JSONObject) lModTreeViewJSONView.get("Root");			
				
				System.out.format("%-15s %-30s %-1s%n", "Attribute Name", "Attribute Ref", "Attribute Actual Scale");
				System.out.format("%-15s %-30s %-1s%n",  "--------------", "------------------------------", "-------------");
				printTree(lModTreeViewJSONRoot);
				
				
			}catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	static void printTree(JSONObject lRoot) {
		if(lRoot.containsKey("Inputs")) {
			JSONArray lRootInputs = (JSONArray) lRoot.get("Inputs");
			for (Object object : lRootInputs) {
				JSONObject lRootInput = (JSONObject) object;
				printTreeAtt(lRootInput);
			}
		}

	}
	
	static void printTreeAtt(JSONObject lInput) {
		JSONObject lJSONAttNode = (JSONObject) lInput.get("Node");
		String lAttName = (String) lJSONAttNode.get("Name");
		String lAttRef = (String)((JSONObject)lJSONAttNode.get("Attribute")).get("Ref");
		String lAttScale = (String) (lJSONAttNode.containsKey("Scale") ? lJSONAttNode.get("Scale") : "No Scale");
		System.out.format("%-15s %-30s %-1s %n", lAttName, lAttRef, lAttScale);
		
		DexAttribute lCurrentAtt = (DexAttribute) lModEditor.RefToObject(lAttRef);
		
		ArrayList<DexScale> allDiffAssScales = lCurrentAtt.AllDifferentAssignableScales(allScales);
		
		for (DexScale dexScale : allDiffAssScales) {
			System.out.format("%-15s %-30s %-1s %n", "", "", dexScale.ToJsonString(lSettings));
		}
		
		if(lJSONAttNode.containsKey("Inputs")) {
			JSONArray lAttInputs = (JSONArray) lJSONAttNode.get("Inputs");
			for (Object input : lAttInputs) {
				printTreeAtt((JSONObject) input);
			}
		}		
	}

}
