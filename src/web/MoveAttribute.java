package web;

import DEX.DexAttribute;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexViewSettings;

public class MoveAttribute {
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
				lModTreeView.setModel(lModel);
				lModEditor.ViewToJson(lModTreeView, lSettings);

				DexAttribute lAttDoors = (DexAttribute) lModEditor.RefToObject("Att__DOORS");

				DexAttribute lAttPrice = (DexAttribute) lModEditor.RefToObject("Att_PRICE");
				System.out.println(lAttDoors + " " + lAttPrice);
				System.out.println(lAttPrice.getInpCount());

				lModEditor.AddInputTo(lAttDoors, lAttPrice);

			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}
}
