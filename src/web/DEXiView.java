package web;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import DEX.DexAttribute;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelInfoView;
import DEX.DexModelTreeNode;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexScale;
import DEX.DexScaleView;
import DEX.DexViewSettings;

public class DEXiView {

	static DexAttribute lAttForCopyPaste = null;
	
	public static void main(String[] args) {
		DexProjectEditor lEditor = new DexProjectEditor(null);
		DexModel lModel = new DexModel(null);
		DexModelEditor lModEditor = new DexModelEditor(null);
		DexProject lProject = new DexProject(null);
		DexViewSettings lSettings = new DexViewSettings(true);
		DexModelTreeView lModTreeView = new DexModelTreeView(null);
		DexModelInfoView lModInfoView = new DexModelInfoView(null);
		DexScaleView lScaleView = null;
		DexAttribute lAtt = null;
		
		try {
			lEditor.LoadProject("demo.dxp");
			lProject = lEditor.getProject();
			System.err.println(lProject.ToJsonString(lModEditor.getRegistry(), lSettings));
			lModel = lProject.getModels()[1];
			
			System.out.println("Model Name: " + lModel.getName());
			
			lModTreeView.setModel(lModel);
			lModInfoView.setModel(lModel);
					
			lModEditor.BeginEditing();
			
			try {
				lModEditor.EditModel(lModel);
				lModEditor.ViewToJson(lModTreeView, lSettings);
				System.out.println(lModInfoView.ToJsonString(lSettings));
				System.out.println(lModTreeView.ToJsonString(lSettings));
				DexModelTreeNode lCurrentNode = lModTreeView.getCurrentNode();
				
				if(lCurrentNode == null) {
					lAtt = (DexAttribute) lModEditor.RefToObject("Att_COMFORT");
					System.out.println("DexAttribute name: " + lAtt.getName());
					lCurrentNode = lModTreeView.getRoot().AttNode(lAtt);

				}
				
				System.out.println("Current Node: " + lCurrentNode.getName());
				
				// Getting Node from JSONResult
				String JSONCurrentNode = lCurrentNode.ToJsonString(lSettings);
				
				System.out.println(JSONCurrentNode);
				
				JSONObject lCurrentJSON = (JSONObject) JSONValue.parse(JSONCurrentNode);
				JSONArray lJSONCurrentDexObjectArr = (JSONArray) lCurrentJSON.get("DexObject");
				JSONObject lJSONCurrentDexObject = (JSONObject) lJSONCurrentDexObjectArr.get(0);
				JSONObject lJSONCurrentNode = (JSONObject) lJSONCurrentDexObject.get("Node");
				
				// What do we need from current node to be shown in a DEXi like view:
				// Attribute Section Its name and description
				System.out.println("\n=== Attribute ===");
				System.out.println("Name: " + lJSONCurrentNode.get("Name"));
				System.out.println("Description: " + lJSONCurrentNode.get("Description"));
				
				// All Different Assignable Scales
				System.out.println("\n=== Scale ===");
				
				System.out.println("Actual Scale: " + lJSONCurrentNode.get("Scale") + "\n");
				System.out.println("Actual Scale as JSON: " + lCurrentNode.getAttribute().getScale().ToJsonString(lSettings) + "\n");
				
				
				
				System.out.println();
				ArrayList<DexScale> lAllScales = lModel.getRoot().AllScales(false);
				ArrayList<DexScale> lAllDiffAssignableScales = lCurrentNode.getAttribute().AllDifferentAssignableScales(lAllScales);
				
//				String s = null;
				
				for (DexScale dexScale : lAllDiffAssignableScales) {
					lScaleView = new DexScaleView(null);
					lScaleView.setScale(dexScale);
//					lProject.AddView(lScaleView);
					JSONObject lScaleJSON = (JSONObject) JSONValue.parse(lScaleView.ToJsonString(lModEditor.getRegistry(),lSettings));
					JSONArray lScaleDexObj = (JSONArray) lScaleJSON.get("DexObject");
					JSONObject lScaleJSONView = (JSONObject) ((JSONObject) lScaleDexObj.get(0)).get("View");
					System.out.println(lScaleJSONView.get("Display") + " " + ((JSONObject)lScaleJSONView.get("Scale")).get("Ref"));					
				}
				
				DexScale lScale = (DexScale) lModEditor.getRegistry().Find("Scl__1");
				lScaleView.setScale(lScale);
				System.out.println(lScaleView.ToJsonString(lModEditor.getRegistry(), lSettings));
				
				System.out.println("\n=== Function ===");
				
				System.out.println(lJSONCurrentNode.get("Function"));
				System.out.println("Decription: ");
				
				System.out.println("\n=== Buttons Status ===");
				toolsState(lModEditor, lModTreeView, lAtt);
				
				initCopy(lAtt);
				
				toolsState(lModEditor, lModTreeView, lAtt);
				
				lModEditor.AddInputTo(lCurrentNode.getAttribute(), lAttForCopyPaste);
				
				initCopy(lAttForCopyPaste);
				
				lModEditor.AddInputTo(lCurrentNode.getAttribute(), lAttForCopyPaste);
				
				lEditor.Save();
				
			} catch (Exception e) {
				System.out.println("Here");
				System.err.println(e.getMessage());
			} 
			
		} catch (Exception e) {
			System.out.println("Here1");
			System.err.println(e.getMessage());
		} finally {
			lEditor.EndEditing();
		}

	}
	
	static void toolsState(DexModelEditor lModEditor, DexModelTreeView lModTreeView, DexAttribute lAtt){
		
		System.out.println("Add Button: " + ( lAtt.CanAddInput() ? "enabled" : "disabled"));
		System.out.println("Delete Button: " + ( lAtt.CanDeleteInputs() ? "enabled" : "disabled"));
		System.out.println("Cut Button: " + "enabled");
		System.out.println("Copy Button: " + "enabled");
		System.out.println("Paste Button: " + ( lAttForCopyPaste != null ? "enabled" : "disabled"));
		System.out.println("Duplicate Button: " + "enabled");
		System.out.println("MoveUp Button: " + ( lModEditor.CanMoveInputOfPrior(lAtt.getParent(), lAtt) ? "enabled" : "disabled"));
		System.out.println("MoveDown Button: " + ( lModEditor.CanMoveInputOfNext(lAtt.getParent(), lAtt) ? "enabled" : "disabled"));
		System.out.println("Shtrink Button: " + ( lModTreeView.CanDecreaseLevels() ? "enabled" : "disabled"));
		System.out.println("Expand Button: " + ( lModTreeView.CanIncreaseLevels() ? "enabled" : "disabled"));		
	}
	
	static void initCopy(DexAttribute lAtt) {
		lAttForCopyPaste = (DexAttribute) lAtt.Copy(false, null);
		System.out.println("\nCopied to clipboard:\n"+lAttForCopyPaste.getName());
	}

}
