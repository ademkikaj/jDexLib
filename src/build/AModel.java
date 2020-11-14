package build;

import java.util.ArrayList;

import DEX.DexAlternative;
import DEX.DexAlternatives;
import DEX.DexAttribute;
import DEX.DexCategoricalScale;
import DEX.DexDataCell;
import DEX.DexDataHeader;
import DEX.DexEvalSettings;
import DEX.DexIntInterval;
import DEX.DexIntSingle;
import DEX.DexModel;
import DEX.DexModelEditor;
import DEX.DexModelTreeView;
import DEX.DexProject;
import DEX.DexProjectEditor;
import DEX.DexScale;
import DEX.DexScaleEditor;
import DEX.DexScaleStrings;
import DEX.DexTabularValueFunction;
import DEX.DexValue;
import DEX.DexViewSettings;
import Utils.Values;

public class AModel {

	static DexProjectEditor lProjectEditor;
	static DexProject lProject;
	static DexModelEditor lModelEditor;
	static DexModel lModel;
	static DexModelTreeView lModelTreeView;
	static DexViewSettings lSettings;
	static DexScaleEditor lScaleEditor;
	
	public static void main(String[] args) {
		lProjectEditor = new DexProjectEditor(null);
		lModelEditor = new DexModelEditor(null);
		lModelTreeView = new DexModelTreeView(null);
		lSettings = new DexViewSettings(true);
		lScaleEditor = new DexScaleEditor(null);
		
		lProjectEditor.BeginEditing();
		lProjectEditor.NewProject("A");
		lProject = lProjectEditor.getProject();
//		getProjectInfo(lProject);
		
		lModel = lProject.getModels()[0];
		lModelEditor.BeginEditing();	
		
		lModelEditor.EditModel(lModel);
		
		// ==== BUILD TREE VIEW AND REGISTER ====
		lModelTreeView.setModel(lModel);
		lModelEditor.ViewToJson(lModelTreeView, lSettings);
		
		// ==== GET THE ROOT ATTRIBUTE====
		DexAttribute A = (DexAttribute) lModelEditor.RefToObject("Att_A");
		
		// ==== ADD TWO ATTRIBUTES ====
		DexAttribute A1 = new DexAttribute(null);
		A1.setName("A1");
		lModelEditor.AddInputTo(A, A1);
		
		DexAttribute A2 = new DexAttribute(null);
		A2.setName("A2");
		lModelEditor.AddInputTo(A, A2);
		
		lModelTreeView.Rearrange();
		lModelEditor.ViewToJson(lModelTreeView, lSettings);
		
		// ==== DEFINE SCALE OF ALL ATTRIBUTES ====
		DexAttribute ARef = (DexAttribute) lModelEditor.RefToObject("Att_A");
		
		ARef.setScale(new DexCategoricalScale(null));		
		DexCategoricalScale AScale = (DexCategoricalScale) ARef.getScale();
		lScaleEditor.BeginEditing();
		lScaleEditor.EditScale(AScale);
		lScaleEditor.AddCategory("Bad", null);
		lScaleEditor.AddCategory("Good", null);
		lScaleEditor.EndEditing();
		
		DexAttribute A1Ref = (DexAttribute) lModelEditor.RefToObject("Att_A1");
		
		A1Ref.setScale(new DexCategoricalScale(null));
		DexCategoricalScale A1Scale = (DexCategoricalScale) A1Ref.getScale();
		lScaleEditor.BeginEditing();
		lScaleEditor.EditScale(A1Scale);
		lScaleEditor.AddCategory("Bad", null);
		lScaleEditor.AddCategory("Good", null);
		lScaleEditor.EndEditing();
		
		DexAttribute A2Ref = (DexAttribute) lModelEditor.RefToObject("Att_A2");
		
		A2Ref.setScale(new DexCategoricalScale(null));
		DexCategoricalScale A2Scale = (DexCategoricalScale) A2Ref.getScale();
		lScaleEditor.BeginEditing();
		lScaleEditor.EditScale(A2Scale);
		lScaleEditor.AddCategory("Bad", null);
		lScaleEditor.AddCategory("Good", null);
		lScaleEditor.EndEditing();
		
		lModelTreeView.Rearrange();
		lModelEditor.ViewToJson(lModelTreeView, lSettings);
		
		// ==== DEFINE FUNCTION OF ROOT ====
		
		DexTabularValueFunction lFunc = new DexTabularValueFunction(null);
		lFunc.DefineOnSpace(ARef.getSpace());
		ARef.setFunct(lFunc);
		DexIntSingle val = new DexIntSingle(null);
		val.setValue(Values.IntSingle(0));
		editValue(ARef, 0, val);
		editValue(ARef, 1, val);
		DexIntSingle val1 = new DexIntSingle(null);
		val1.setValue(Values.IntSingle(1));
		editValue(ARef, 2, val1);
		editValue(ARef, 3, val1);
		
		// ==== SHOW FUNCTION OF ROOT ====
		
		functionView(ARef);		
				
		// ==== SHOW ALL ALTERNATIVES ====
		DexAlternatives lAllAlternatives = lModel.getAlternatives();
		showAlternatives(lAllAlternatives);
		
		// ==== PREPARE ALTERNATIVES HEADER ====
		
		addHeader(lModel);
		
		// ==== ADD TWO ALTERNATIVES ====
		
		DexAlternative lNewAlt1 = new DexAlternative(null);
		lNewAlt1.setName("Alt1");
		lAllAlternatives.AddAlternative(lNewAlt1);
		
		DexDataCell lDDC = new DexDataCell(null);
		DexIntSingle value = new DexIntSingle(null);
		value.setValue(Values.IntSingle(0));
		lDDC.setValue(value);
		lNewAlt1.InsertEntry(lDDC, 1);
		lNewAlt1.InsertEntry(lDDC, 2);
		
		DexAlternative lNewAlt2 = new DexAlternative(null);
		lNewAlt1.setName("Alt2");
		lAllAlternatives.AddAlternative(lNewAlt2);
		
		DexDataCell lDDC1 = new DexDataCell(null);
		DexDataCell lDDC2 = new DexDataCell(null);
		DexIntSingle value1 = new DexIntSingle(null);
		DexIntInterval interval = new DexIntInterval(null);
		value1.setValue(Values.IntSingle(1));
		interval.setValue(Values.IntInt(0, 1));
		lDDC1.setValue(value1);
		lNewAlt2.InsertEntry(lDDC1, 1);
		lDDC2.setValue(interval);
		lNewAlt2.InsertEntry(lDDC1, 2);
		
		// ==== SHOW ALL ALTERNATIVES ====
		showAlternatives(lAllAlternatives);
		
		// ==== EVALUATE ====
		
		DexEvalSettings lEvalSettings = new DexEvalSettings();
//		lEvalSettings.setEvaluationType(DexEvaluationType.Discrete);
		lModel.Evaluate(lEvalSettings);
		
		// ==== SHOW EVALUATION RESULTS ====
		
		showEvaluation(lModel);
		
		// ==== ADD NEW ATTRIBUTE ====
		
		DexAttribute A3 = new DexAttribute(null);
		A3.setName("A3");
		lModelEditor.AddInputTo(A, A3);
		
		showAlternatives(lAllAlternatives);
		
		lModelEditor.EndEditing();
		
		lProjectEditor.EndEditing();
	}
	
	public static void getProjectInfo(DexProject lProject) {
		System.out.println("Project Name: " + lProject.getName());
		System.out.println("Number of models in Project: " + lProject.getModelCount());
	}

	public static void functionView(DexAttribute lAtt) {
		
		if(lAtt.getFunct() == null) {
			System.err.println(lAtt.getName() + " has no function defined.");
			return;
		}
		
		// Get DexTabularValueFunction of the given attribute
		DexTabularValueFunction lTabularValueFunc = (DexTabularValueFunction) lAtt.getFunct();
		// Create DexScaleStrings used to convert Args to String Scale - Qualitative
		DexScaleStrings lDSS = new DexScaleStrings(null, null);
		// Get the array of Scales of the args of the attribute - Scales of attribute inputs - lAtt.getInputs();
		DexScale[] argsScales = lTabularValueFunc.getArgScales();
		// Get Scale of this attribute
		DexScale lAttrScale = lAtt.getScale();
		int low = lAttrScale.getLowIntBound();
		int high = lAttrScale.getHighIntBound();
		// Get number of rules
		int tabCount = lTabularValueFunc.getTabCount();
		// Print them
		for (int i = 0; i < tabCount; i++) {
			// Get DexValue of in position i
			DexValue v = lTabularValueFunc.getValue(i);			
			// Get args in position i
			int args[] = lTabularValueFunc.ArgsOfIndex(i);
			// save length to iterate
			int argsCount = args.length;
			// DexIntSingle instance used to create a DexValue from args at position 
			DexValue lArgsValue = new DexIntSingle(null);
			System.out.format("%-3d", (i+1));
			for (int j = 0; j < argsCount; j++) {
				// For each argument set as integer to DexValue
				lArgsValue.setAsInteger(args[j]);
				// Print it
				String type;
				if(args[j]==low)
					type = "bad";
				else if(args[j]==high)
					type = "good";
				else
					type = "neutral";
				System.out.format("%-10s", lDSS.ValueOnScaleString(lArgsValue, argsScales[j]));
			}
			// Print the value function
			if(v!=null)
				System.out.println(lDSS.ValueOnScaleString(v, lAttrScale));
			else
				System.out.println("null");
		}
	}
	
	public static void editValue(DexAttribute lAtt, int idx, DexValue v) {
		DexTabularValueFunction lTabularValueFunc = (DexTabularValueFunction) lAtt.getFunct();
		lTabularValueFunc.setValue(idx, v);
	}

	public static void showAlternatives(DexAlternatives lAllAlternatives) {
		int altCount = lAllAlternatives.getAltCount();
		System.out.println("Number of alternatives: " + altCount);
		ArrayList<DexAttribute> terminals = lModel.getRoot().TerminalAttributes();		
		DexScaleStrings lDSS = new DexScaleStrings(null, null);
		DexDataHeader lAlternativesHeader = lAllAlternatives.getHeader();
		for (DexAttribute dexAttribute : terminals) {
			if(altCount > 0)
			{
				for (int i = 0; i < altCount; i++) {
					DexValue v = lAllAlternatives.getValue(i, lAlternativesHeader.HeadIndex(dexAttribute));
					System.out.print(lDSS.ValueOnScaleString(v, dexAttribute.getScale()));
				}
				System.out.println();
			}else
				System.out.println(dexAttribute.getName());
		}
	}

	public static void addHeader(DexModel lModel) {
		DexAlternatives lAlternatives = lModel.getAlternatives();
		ArrayList<DexAttribute> terminals = lModel.getRoot().AllAttributes(false);
		for (DexAttribute dexAttribute : terminals) {
			lAlternatives.AddHead(dexAttribute);
		}
	}
	
	public static void showEvaluation(DexModel lModel) {
		DexAlternatives lAllAlternatives = lModel.getAlternatives();
		int altCount = lAllAlternatives.getAltCount();
		System.out.println("Number of alternatives: " + altCount);
		ArrayList<DexAttribute> terminals = lModel.getRoot().AllAttributes(false);		
		DexScaleStrings lDSS = new DexScaleStrings(null, null);
		DexDataHeader lAlternativesHeader = lAllAlternatives.getHeader();
		for (DexAttribute dexAttribute : terminals) {
			if(altCount > 0)
			{
				for (int i = 0; i < altCount; i++) {
					DexValue v = lAllAlternatives.getValue(i, lAlternativesHeader.HeadIndex(dexAttribute));
					System.out.println(dexAttribute.getName() + " " +lDSS.ValueOnScaleString(v, dexAttribute.getScale()));
				}
			}else
				System.out.println(dexAttribute.getName());
		}
	}
}
