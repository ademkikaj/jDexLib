import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import DEX.DexAttribute;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeNode;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexScale;
import DEX.DexViewSettings;

public class DexScaleJSON {

	public static void main(String[] args) {
		DexProjectEditor lEditor = new DexProjectEditor(null);
		DexModel lModel = new DexModel(null);
		DexModelEditor lModEditor = new DexModelEditor(null);
		DexProject lProject = new DexProject(null);
		DexViewSettings lSettings = new DexViewSettings(true);
		DexModelTreeView lModTreeView = new DexModelTreeView(null);
		
		try {
			lEditor.LoadProject("demo.dxp");
			lProject = lEditor.getProject();
			lModel = lProject.getModels()[1];
			System.out.println(lModel.getName());
			
			lModEditor.EditModel(lModel);
			
			lModTreeView.setModel(lModel);
			
			lModEditor.ViewToJson(lModTreeView, lSettings);
						
			ArrayList<DexScale> allScales = lModel.getRoot().AllScales(false);
			
			DexAttribute lComfortAtt = (DexAttribute) lModEditor.RefToObject("Att_COMFORT");			
			
			ArrayList<DexScale> lAssignableScales = lComfortAtt.AllDifferentAssignableScales(allScales);
			
			System.out.println(lAssignableScales.size());
			
			for (DexScale dexScale : lAssignableScales) {
				System.out.println(dexScale.ToJsonString(lSettings));
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
