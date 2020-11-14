package web;

import DEX.DexAttribute;
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
import Utils.Formatting;

public class ScaleEditor {
	static DexProjectEditor lEditor = new DexProjectEditor(null);
	static DexModel lModel = new DexModel(null);
	static DexModelEditor lModEditor = new DexModelEditor(null);
	static DexProject lProject = new DexProject(null);
	static DexViewSettings lSettings = new DexViewSettings(true);
	static DexModelTreeView lModTreeView = new DexModelTreeView(null);
	static DexScaleEditor lScaleEditor = new DexScaleEditor(null);

	public static void main(String[] args) {
		try {
//			lEditor.LoadProject("demo.dxp");
			lEditor.BeginEditing();
			lEditor.NewProject("P");
			lEditor.AddDEXiModel("CarFunction.dxi");
			lProject = lEditor.getProject();
			lModel = lProject.getModels()[1];

			System.out.println("Model Name: " + lModel.getName());

			lModTreeView.setModel(lModel);

			lModEditor.BeginEditing();

			try {
				lModEditor.EditModel(lModel);
				String view = lModEditor.ViewToJson(lModTreeView, lSettings);

				String lModTreeViewString = lModTreeView.ToJsonString(lSettings);
				DexAttribute lAttrCar = (DexAttribute) lModEditor.RefToObject("Att_CAR");
				DexAttribute lAttrPrice = (DexAttribute) lModEditor.RefToObject("Att_PRICE");
				DexAttribute lAttrBuyPrice = (DexAttribute) lModEditor.RefToObject("Att_BUY_PRICE");
				DexAttribute lAttrComfort = (DexAttribute) lModEditor.RefToObject("Att_COMFORT");
				
				showScaleDexTabularValueFunction(lAttrPrice);
//				lScaleEditor.EditScale(lAttrBuyPrice.getScale());
//				lScaleEditor.DeleteCategory(0);
//				showScaleDexTabularValueFunction(lAttrPrice);
				

			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void showScaleDexTabularValueFunction(DexAttribute attribute)
	{
		// Here I need a check if the given attribute has a function? lAtt.isFuncDefined() maybe
		// Get DexTabularValueFunction of the given attribute
		DexTabularValueFunction lTabularValueFunc = (DexTabularValueFunction) attribute.getFunct();
		// Create DexScaleStrings used to convert Args to String Scale - Qualitative
		DexScaleStrings lDSS = new DexScaleStrings(null, null);
		// Get the array of Scales of the args of the attribute - Scales of attribute inputs - lAtt.getInputs();
		DexScale[] argsScales = lTabularValueFunc.getArgScales();
		// Get Scale of this attribute
		DexScale lAttrScale = attribute.getScale();
		// Get number of rules
		int tabCount = lTabularValueFunc.getTabCount();
		// Print them
		for (int i = 0; i < tabCount; i++) {
			// Get DexValue of in position i
			DexValue v = lTabularValueFunc.getValue(i);
			
			// Get args in position i
			int args[] = lTabularValueFunc.ArgsOfValue(v);
			// save length to iterate
			int argsCount = args.length;
			// DexIntSingle instance used to create a DexValue from args at position 
			DexValue lArgsValue = new DexIntSingle(null);
			System.out.format("%-3d", (i+1));
			for (int j = 0; j < argsCount; j++) {
				// For each argument set as integer to DexValue
				lArgsValue.setAsInteger(args[j]);
				// Print it
				System.out.format("%-10s", lDSS.ValueOnScaleString(lArgsValue, argsScales[j]));
			}
			// Print the value function
			System.out.println(lDSS.ValueOnScaleString(v, lAttrScale));
		}
	}
}
