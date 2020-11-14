package function;

import DEX.DexAttribute;
import DEX.DexCategoricalScale;
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
import Utils.FltFlt;
import Utils.FltMem;
import Utils.Formatting;
import Utils.IntInt;
import Utils.OffMem;
import Utils.PrefCompare;
import Utils.Values;

public class FunctionShowAndEdit {

	
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
			try {
				lModEditor.EditModel(lModel);
				lModTreeView.setModel(lModel);
				lModEditor.ViewToJson(lModTreeView, lSettings);
//				System.err.println(lModTreeView.ToJsonString(lSettings));
				
				DexAttribute lAttCar = (DexAttribute) lModEditor.RefToObject("Att_CAR");
				DexCategoricalScale lCatScale = (DexCategoricalScale) lAttCar.getScale();
				functionView(lAttCar);
				// set from scale category
//				DexIntSingle val = new DexIntSingle(null);
				DexIntInterval val = new DexIntInterval(null);
				val.setValue(Values.IntInt(0,3));
//				val.setAsInteger(3);
				editValue(lAttCar, 0, val);
				System.out.println();
				functionView(lAttCar);
//				editValue(lAttCar, 0, null);
//				editValue(lAttCar, 2, new DexIntSingle(null));
//				editValue(lAttCar, 3, new DexIntSingle(null));
//				editValue(lAttCar, 4, new DexIntSingle(null));
//				System.out.println();
//				functionView(lAttCar);
				
				
			} catch (Exception e) {
				System.err.println(e);
			}finally {
				lModEditor.EndEditing();
			}
		}catch(Exception e) {
			System.err.println(e);
		}
		finally {
			lEditor.EndEditing();
		}
	}
	
	static void functionView(DexAttribute lAtt) {
		
		if(lAtt.getFunct() == null) {
			System.err.println(lAtt.getName() + " has no function defined.");
			return;
		}
		
		System.out.format("%-3s", "");
		int inputNr = lAtt.getInpCount();
		for (int i = 0; i < inputNr; i++) {
			System.out.format("%-10s", lAtt.getInput(i).getName());
		}
		System.out.format("%-10s", lAtt.getName());
		System.out.println();
		
		// Get DexTabularValueFunction of the given attribute
		DexTabularValueFunction lTabularValueFunc = (DexTabularValueFunction) lAtt.getFunct();
		
		
		// Create DexScaleStrings used to convert Args to String Scale - Qualitative
		DexScaleStrings lDSS = new DexScaleStrings(null, null);
		

		
		// Get the array of Scales of the args of the attribute - Scales of attribute inputs - lAtt.getInputs();
		DexScale[] argsScales = lTabularValueFunc.getArgScales();
		// Get Scale of this attribute
		DexScale lAttrScale = lAtt.getScale();		
		System.err.println(lDSS.ValueOnScaleString(lTabularValueFunc.getValue(0), lAttrScale));
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
				lArgsValue.setAsInteger(args[j]);		
				int low = argsScales[j].getLowIntBound();
				int high = argsScales[j].getHighIntBound();		
				// Print it
				String type;
				if(args[j]==low)
					type = "bad";
				else if(args[j]==high)
					type = "good";
				else
					type = "neutral";
				System.out.format("%-10s", type + "-" + lDSS.ValueOnScaleString(lArgsValue, argsScales[j]));
			}
			// Print the value function
			if(v!=null)
				System.out.println("="+lDSS.ValueOnScaleString(v, lAttrScale));
			else
				System.out.println("null");
		}
	}

	static void editValue(DexAttribute lAtt, int idx, DexValue v) {
		DexTabularValueFunction lTabularValueFunc = (DexTabularValueFunction) lAtt.getFunct();
		lTabularValueFunc.setValue(idx, v);
	}
}
