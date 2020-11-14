package scale;

import java.util.ArrayList;

import DEX.DexAttribute;
import DEX.DexCategoricalScale;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexScale;
import DEX.DexScaleEditor;
import DEX.DexScaleView;
import DEX.DexViewSettings;

public class AllAssiganbleScales {

	static DexProjectEditor lEditor = new DexProjectEditor(null);
	static DexModel lModel = new DexModel(null);
	static DexModelEditor lModEditor = new DexModelEditor(null);
	static DexProject lProject = new DexProject(null);
	static DexViewSettings lSettings = new DexViewSettings(true);
	static DexModelTreeView lModTreeView = new DexModelTreeView(null);
	static DexScaleEditor lScaleEditor = new DexScaleEditor(null);
	static ArrayList<DexScale> allScales = null;
	static ArrayList<DexScale> allDiffAssScales = null;
	
	public static void main(String[] args) {
		lEditor.NewProject("Demo");
		lProject = lEditor.getProject();
		lModel = lProject.getModels()[0];
		
		try {
			lModEditor.EditModel(lModel);
			
			lModTreeView.setModel(lModel);
			lModEditor.ViewToJson(lModTreeView, lSettings);
			
			DexAttribute lAttDemo = (DexAttribute) lModEditor.RefToObject("Att_Demo");
			lAttDemo.setName("A");			
			addDefaultScale(lAttDemo);
			scaleState(lAttDemo);			
			addAttribute("A1", lAttDemo);
			DexAttribute lAttA1 = (DexAttribute)lModEditor.RefToObject("Att_A1");
			System.out.println();
			addDefaultScale1(lAttA1);

			scaleState(lAttA1);
			addAttribute("A2", lAttDemo);
			DexAttribute lAttA2 = (DexAttribute) lModEditor.RefToObject("Att_A2");
			System.out.println();
			scaleState(lAttA2);
			
			DexCategoricalScale lScale1 = new DexCategoricalScale(null);
			lScale1.AddCategory("Bad", "");
			lScale1.AddCategory("Good", "");
			
			DexScale lByRef = (DexScale) lModEditor.RefToObject("Scl__1");
			lModEditor.SetScaleOf(lAttA2, lByRef);
			System.err.println("aaa");
			scaleState(lAttA2);
			scaleState(lAttA1);
			
			
		} catch (Exception e) {
			System.err.println(e);
		}

	}
	
	static void addAttribute(String name, DexAttribute addTo) {
		DexAttribute lAttA1 = new DexAttribute(null);
		lAttA1.setName(name);
		lModEditor.AddInputTo(addTo, lAttA1);
		lModTreeView.Rearrange();
		lModEditor.ViewToJson(lModTreeView, lSettings);
	}
	
	static void addDefaultScale(DexAttribute lAtt) {
		DexCategoricalScale lAttDemoScale = (DexCategoricalScale) lAtt.getScale();
		if(lAttDemoScale == null) {
			lAtt.setScale(new DexCategoricalScale(null));
			lAttDemoScale = (DexCategoricalScale) lAtt.getScale();
			try {
				lScaleEditor.EditScale(lAttDemoScale);
				lScaleEditor.AddCategory("New Category Bad", "");
				lScaleEditor.AddCategory("New Category Good","");
			} catch (Exception e) {
				System.err.println(e);
			}finally {
				lScaleEditor.EndEditing();
			}
		}
	}

	static void addDefaultScale1(DexAttribute lAtt) {
		DexCategoricalScale lAttDemoScale = (DexCategoricalScale) lAtt.getScale();
		if(lAttDemoScale == null) {
			lAtt.setScale(new DexCategoricalScale(null));
			lAttDemoScale = (DexCategoricalScale) lAtt.getScale();
			try {
				lScaleEditor.EditScale(lAttDemoScale);
				lScaleEditor.AddCategory("Bad", "");
				lScaleEditor.AddCategory("Good","");
			} catch (Exception e) {
				System.err.println(e);
			}finally {
				lScaleEditor.EndEditing();
			}
		}
	}
	
	static void scaleState(DexAttribute lAtt) {
		allScales = lModel.getRoot().AllScales(false);
		if(lAtt.getScale() == null)
			System.out.println("This attribute "+ lAtt.getName() +" has no scale.");
		else
			System.out.println("This attributes "+ lAtt.getName() +" scale: " + lAtt.getScale().ToJsonString(lSettings));
		allDiffAssScales = lAtt.AllDifferentAssignableScales(allScales);
		System.out.println("Nr of possible assignable scales: " + allDiffAssScales.size());	
		
		for (DexScale dexScale : allDiffAssScales) {
			DexScaleView lScaleView = new DexScaleView(null);
			lScaleView.setScale(dexScale);
			String s = lScaleView.ToJsonString(lModEditor.getRegistry(), lSettings);
			System.out.println(s);
		}
	}

}
