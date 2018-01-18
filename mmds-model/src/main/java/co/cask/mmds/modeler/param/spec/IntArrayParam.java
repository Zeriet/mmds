package co.cask.mmds.modeler.param.spec;

import java.util.Map;

/**
 * An integer Modeler parameter.
 */
public class IntArrayParam extends Param<int[]> {
  private final ParamSpec spec;

  public IntArrayParam(String name, String label, String description, int[] defaultVal,
                       Map<String, String> params) {
    super(name, description, defaultVal, params);
    StringBuilder defaultStr = new StringBuilder();
    if (defaultVal.length > 0) {
      defaultStr.append(defaultVal[0]);
    }
    for (int i = 1; i < defaultVal.length; i++) {
      defaultStr.append(',').append(defaultVal[i]);
    }
    spec = new ParamSpec("intarray", name, label, description, defaultStr.toString(), null, null);
  }

  @Override
  protected int[] parseVal(String strVal) {
    String[] parts = strVal.split(",");
    int[] layers = new int[parts.length];
    for (int i = 0; i < parts.length; i++) {
      String trimmed = parts[i].trim();
      try {
        int layer = Integer.parseInt(trimmed);
        if (layer < 1) {
          throw new IllegalArgumentException(
            String.format("Invalid modeler parameter %s=%s. Must be a comma separate list of positive integers.",
                          name, strVal));
        }
        layers[i] = layer;
      } catch (NumberFormatException e) {
        throw new NumberFormatException(
          String.format("Invalid modeler parameter %s=%s. Must be a comma separate list of positive integers.",
                        name, strVal));
      }
    }
    return layers;
  }

  @Override
  public ParamSpec getSpec() {
    return spec;
  }
}
