package util.common;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
public class EncapsulationObject {
	@SuppressWarnings("unchecked")
	public static Object getObject(String[] selectColumns, Class  entityClass, Object[] dataObjs) {
		Object obj = null;
		try {
			obj = entityClass.newInstance();
			for (int i=0;i<selectColumns.length;i++) {
				String setMethod = "set" + selectColumns[i].substring(0, 1).toUpperCase() + selectColumns[i].substring(1);
				Field field= entityClass.getDeclaredField(selectColumns[i]);
				Method m=entityClass.getDeclaredMethod(setMethod,field.getType());
				m.invoke(obj, dataObjs[i]);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
