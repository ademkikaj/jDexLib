package web;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import DEX.DexAttribute;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeNode;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexViewSettings;

public class Charts {

	static DexProjectEditor lEditor = new DexProjectEditor(null);
	static DexModel lModel = new DexModel(null);
	static DexModelEditor lModEditor = new DexModelEditor(null);
	static DexProject lProject = new DexProject(null);
	static DexViewSettings lSettings = new DexViewSettings(true);
	static DexModelTreeView lModTreeView = new DexModelTreeView(null);
	static Iterable<DexModelTreeNode> lAllSelectedIterator = null;
	static ArrayList<DexModelTreeNode> lAllSelected = new ArrayList<>();
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
				
				
				//Setting selected to true for attribute PRICE, TECH.CHAR. and #PERS
				
				DexAttribute lPrice = (DexAttribute) lModEditor.RefToObject("Att_PRICE");
				DexAttribute lTechChar = (DexAttribute) lModEditor.RefToObject("Att_TECH_CHAR_");
				DexAttribute lPers = (DexAttribute) lModEditor.RefToObject("Att__PERS");
				
				lModTreeView.getRoot().AttNode(lPrice).setIsSelected(true);
				lModTreeView.getRoot().AttNode(lTechChar).setIsSelected(true);
				lModTreeView.getRoot().AttNode(lPers).setIsSelected(true);
				
				System.out.println(lModTreeView.ToJsonString(lSettings));
				lAllSelectedIterator = lModTreeView.getSelectedNodes();

				lAllSelectedIterator.forEach(lAllSelected::add);
				
				
				System.out.format("%-18s %-15s %-1s%n", "Attribute Selected", "Attribute Name", "Attribute Ref");
				System.out.format("%-18s %-15s %-1s%n",  "------------------", "---------------", "-------------");
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
		DexAttribute lCurrentAtt = (DexAttribute) lModEditor.RefToObject(lAttRef);
		DexModelTreeNode lCurrentNode = lModTreeView.getRoot().AttNode(lCurrentAtt);
		boolean lIsCurrentSelected = lAllSelected.indexOf(lCurrentNode) != -1;
		
		System.out.format("%-18s %-15s %-1s %n", lIsCurrentSelected, lAttName, lAttRef);
						
		if(lJSONAttNode.containsKey("Inputs")) {
			JSONArray lAttInputs = (JSONArray) lJSONAttNode.get("Inputs");
			for (Object input : lAttInputs) {
				printTreeAtt((JSONObject) input);
			}
		}		
	}

}
