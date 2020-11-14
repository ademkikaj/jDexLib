package alternatives;

import java.util.ArrayList;

import DEX.DexAlternative;
import DEX.DexAlternatives;
import DEX.DexAttribute;
import DEX.DexDataCell;
import DEX.DexDataHeader;
import DEX.DexIntSingle;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexScaleEditor;
import DEX.DexScaleStrings;
import DEX.DexValue;
import DEX.DexViewSettings;

public class ReadAlternatives {

	static DexProjectEditor lEditor = new DexProjectEditor(null);
	static DexModel lModel = new DexModel(null);
	static DexModelEditor lModEditor = new DexModelEditor(null);
	static DexProject lProject = new DexProject(null);
	static DexViewSettings lSettings = new DexViewSettings(true);
	static DexModelTreeView lModTreeView = new DexModelTreeView(null);
	static DexScaleEditor lScaleEditor = new DexScaleEditor(null);
	
	public static void main(String[] args) {
		try {
			lEditor.BeginEditing();
			lEditor.LoadProject("demo.dxp");
			lProject = lEditor.getProject();
			lModel = lProject.getModels()[1];
			lModel.getRoot();
			try {
				lModEditor.EditModel(lModel);
				
				
				DexAlternatives lAllAlternatives = lModel.getAlternatives();
				
				DexAlternative lAlternative = lAllAlternatives.getAlternative(0);
				
				DexDataHeader lAlternativesHeader = lAllAlternatives.getHeader();
				
				ArrayList<DexAttribute> terminals = lModel.getRoot().TerminalAttributes();
				
				DexAlternative lNewAlt = new DexAlternative(null);
				lAllAlternatives.AddAlternative(lNewAlt);
				
				DexDataCell lDDC = new DexDataCell(null);
				DexValue vv = new DexIntSingle(null);
				vv.setAsInteger(0);
				lDDC.setValue(vv);
				lNewAlt.InsertEntry(lDDC, 3);
				
				DexScaleStrings lDSS = new DexScaleStrings(null, null);
				for (DexAttribute dexAttribute : terminals) {
					System.out.println(lAlternativesHeader.HeadIndex(dexAttribute));
//					DexValue v = lAllAlternatives.getValue(0, lAlternativesHeader.HeadIndex(dexAttribute));
//					DexValue v1 = lAllAlternatives.getValue(2, lAlternativesHeader.HeadIndex(dexAttribute));
//					System.out.println(dexAttribute.getName() + " " +lDSS.ValueOnScaleString(v, dexAttribute.getScale()) + " " +lDSS.ValueOnScaleString(v1, dexAttribute.getScale()));
				}
				
				
				
				
			}catch(Exception e) {
				System.err.println(e);
			}
		}catch(Exception e) {
			System.err.println(e);
		}
	}

}
