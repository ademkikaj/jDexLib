package web;

import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeNode;
import DEX.DexModelTreeView;
import DEX.DexModelView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexViewSettings;

public class CurrentAttributeJSON {

	static DexProjectEditor lEditor = new DexProjectEditor(null);
	static DexModel lModel = new DexModel(null);
	static DexModelEditor lModEditor = new DexModelEditor(null);
	static DexProject lProject = new DexProject(null);
	static DexViewSettings lSettings = new DexViewSettings(true);
	static DexModelTreeView lModTreeView = new DexModelTreeView(null);

	public static void main(String[] args) {

		lEditor.BeginEditing();
		try {
			lEditor.LoadProject("demo.dxp");
			lProject = lEditor.getProject();
			lEditor.ProjectViewJson(lSettings);
			lModel = lEditor.RefToModel("Mod_CAR");
			lModEditor.BeginEditing();
			try {
				lModEditor.EditModel(lModel);

				DexModelView[] views = lModEditor.getModelViews();

				for (DexModelView dexModelView : views) {
					if (dexModelView instanceof DexModelTreeView)
						lModTreeView = (DexModelTreeView) dexModelView;
				}

				System.out.println(lModEditor.ViewToJson(lModTreeView, lSettings));

				DexModelTreeNode lAttCurrent = lModTreeView.getCurrentNode();

				System.out.println(lAttCurrent.getName());

				System.out.println(lAttCurrent.ToJsonString(lSettings));

			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

}
