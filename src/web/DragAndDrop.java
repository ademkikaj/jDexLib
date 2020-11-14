package web;

import DEX.DexAttribute;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexViewSettings;

public class DragAndDrop {

	public static void main(String[] args) {
		DexProjectEditor lEditor = new DexProjectEditor(null);
		DexModelEditor lModEditor = new DexModelEditor(null);
		DexProject lProject = new DexProject(null);
		DexModel lModel = new DexModel(null);
		DexViewSettings lSettings = new DexViewSettings(true);
		DexModelTreeView lModTreeView = new DexModelTreeView(null);
		
		try {
			lEditor.BeginEditing();
			lEditor.LoadProject("demo.dxp");
			lProject = lEditor.getProject();		
			lEditor.ProjectViewJson(lSettings);
			lModel = lEditor.RefToModel("Mod_CAR");
			
			try {
				lModEditor.BeginEditing();
				lModEditor.EditModel(lModel);
				lModTreeView.setModel(lModel);
				lModEditor.ViewToJson(lModTreeView, lSettings);
				
				DexAttribute lPrice = (DexAttribute) lModEditor.RefToObject("Att_PRICE");
				System.out.println(lPrice.getName() + " " + lPrice.getInpCount());
				
				DexAttribute lSafety = (DexAttribute) lModEditor.RefToObject("Att_SAFETY");
				DexAttribute lSafetyC = (DexAttribute) lSafety.Copy(false, null);
				
				lPrice.AddInput(lSafetyC);
				System.out.println(lPrice.getName() + " " + lPrice.getInpCount());
				
				DexAttribute lSafetyParent = lSafety.getParent();
				
				System.out.println(lSafetyParent.getName() + " " + lSafetyParent.getInpCount());
				lModEditor.DeleteAttribute(lSafety);
				System.out.println(lSafetyParent.getName() + " " + lSafetyParent.getInpCount());
				
				lModTreeView.Rearrange();
				
				System.out.println(lModTreeView.ToJsonString(lSettings));
				
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

}
