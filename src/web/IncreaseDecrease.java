package web;

import DEX.DexAttribute;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexViewSettings;

public class IncreaseDecrease {

	public static void main(String[] args) {
		DexProjectEditor lEditor = new DexProjectEditor(null);
		DexModelEditor lModEditor = new DexModelEditor(null);
		DexProject lProject = new DexProject(null);
		DexModel lModel = new DexModel(null);
		DexViewSettings lSettings = new DexViewSettings(true);
		DexModelTreeView lModTreeView = new DexModelTreeView(null);
		DexAttribute lAtt = null;
		
		try {
			lEditor.BeginEditing();
			lEditor.NewProject("IncreaseDecrease");
			lProject = lEditor.getProject();		
			lProject.getModels()[0].Delete();
			lEditor.AddDEXiModel("A.dxi");
			lEditor.ProjectViewJson(lSettings);
			lModel = lEditor.RefToModel("Mod_A_dxi");
			
			try {
				lModEditor.BeginEditing();
				lModEditor.EditModel(lModel);
				lModTreeView.setModel(lModel);
				lModEditor.ViewToJson(lModTreeView, lSettings);

//				System.out.println(lModTreeView.getLevels() + " " + lModTreeView.getMaxLevels());
				System.err.println(lModTreeView.ToJsonString(lSettings));
				
				System.out.println(lModTreeView.CanDecreaseLevels());
				
				if(lModTreeView.CanDecreaseLevels())
					lModTreeView.DecreaseLevels();
				
				System.err.println(lModTreeView.ToJsonString(lSettings));
//				System.out.println(lModTreeView.getLevels() + " " + lModTreeView.getMaxLevels());
				
				System.out.println(lModTreeView.CanDecreaseLevels());
				
				if(lModTreeView.CanDecreaseLevels())
					lModTreeView.DecreaseLevels();
				
				System.out.println(lModTreeView.CanDecreaseLevels());
				
				if(lModTreeView.CanDecreaseLevels())
					lModTreeView.DecreaseLevels();
				
				System.out.println(lModTreeView.CanDecreaseLevels());
				
				if(lModTreeView.CanDecreaseLevels())
					lModTreeView.DecreaseLevels();
				
				System.out.println(lModTreeView.CanDecreaseLevels());
				
				if(lModTreeView.CanDecreaseLevels())
					lModTreeView.DecreaseLevels();
				
				System.out.println(lModTreeView.CanDecreaseLevels());
				
				if(lModTreeView.CanDecreaseLevels())
					lModTreeView.DecreaseLevels();
				
				
				System.out.println(lModTreeView.CanDecreaseLevels());
				
				if(lModTreeView.CanDecreaseLevels())
					lModTreeView.DecreaseLevels();
				
				
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
