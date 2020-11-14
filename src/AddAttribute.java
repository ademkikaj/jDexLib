import java.util.Arrays;

import DEX.DexAttribute;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexProjectView;
import DEX.DexViewSettings;

public class AddAttribute {

	public static void main(String[] args) {
		DexProjectEditor lEditor = new DexProjectEditor(null);		
		DexModel lModel = new DexModel(null);
		DexModelEditor lModEditor = new DexModelEditor(null);
		DexModelTreeView lModView = new DexModelTreeView(null);
		DexProject lProject = new DexProject(null);
		DexProjectView lProjectView = new DexProjectView(null);
		DexViewSettings lSettings = new DexViewSettings(true);
		
		
		try {
			lEditor.BeginEditing();
			lEditor.LoadProject("demo.dxp");
			lProject = lEditor.getProject();
			
			lModel = lProject.getModels()[1];
			
			lModEditor.BeginEditing();
			
			try {
				lModEditor.EditModel(lModel);
				
				lModView.setModel(lModel);
				
				lModEditor.ViewToJson(lModView, lSettings);
				
				System.out.println(lModView.ToJsonString(lModEditor.getRegistry(), lSettings));
				
				DexAttribute lAttr = (DexAttribute) lModEditor.RefToObject("Att_CAR");
				System.out.println(lAttr.getName());
				// Add new Attribute
				DexAttribute lNewAttr = new DexAttribute(null);
				lNewAttr.setName("New Attribute");
				
				lModEditor.AddInputTo(lAttr, lNewAttr);
				
				lModView.Rearrange();
				
				lModEditor.ViewToJson(lModView, lSettings);
				
				System.out.println(lModView.ToJsonString(lModEditor.getRegistry(),lSettings));
				
				// Delete Attribute
				DexAttribute lDeleteAttr = (DexAttribute) lModEditor.RefToObject("Att_New_Attribute");
				
				System.out.println(lDeleteAttr.getName());
				
				lModEditor.DeleteAttribute(lDeleteAttr);
				
				// Copy Attribute				
				DexAttribute lCopyAttr = (DexAttribute) lModEditor.RefToObject("Att_MAINT_PRICE");
				System.out.println(lCopyAttr.getName());
				DexAttribute lMoveTo = (DexAttribute) lModEditor.RefToObject("Att_CAR");
				System.out.println(lMoveTo.getName());
				
				DexAttribute lNewAttrCopy = lCopyAttr;
				System.out.println(lNewAttrCopy.getName());
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
