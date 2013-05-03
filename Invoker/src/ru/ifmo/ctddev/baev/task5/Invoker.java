package ru.ifmo.ctddev.baev.task5;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class Invoker {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Error! Enter more than 1 argument");
			return;
		}

		String className = args[0];
		Class<?> c;
		try {
			c = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.println("Error! Class not found");
			return;
		}

		int modifier = c.getModifiers();
		if (Modifier.isInterface(modifier)) {
			System.out.println("Error! " + className + " is interface");
			return;
		}
		if (Modifier.isAbstract(modifier)) {
			System.out.println("Error! " + className + " is abstract");
			return;
		}
		if (Modifier.isStatic(modifier)) {
			System.out.println("Error! " + className + " is static");
			return;
		}

		Object obj;
		try {
			obj = c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			System.out.println("Error! can't instance");
			return;
		}

		Object[] args2 = Arrays.copyOfRange(args, 2, args.length);
		String methodName = args[1];
		for (Method method : c.getMethods()) {
			if (method.getName().equals(methodName)) {
				boolean wasError = false;
				Class<?>[] methodArgs = method.getParameterTypes();
				if (methodArgs.length != args2.length) {
					continue;
				}
				for (Class<?> curArg : methodArgs) {
					if (!curArg.equals(Object.class)
							&& !curArg.equals(String.class)) {
						wasError = true;
						break;
					}
				}
				if (!wasError) {
					try {
						method.invoke(obj, args2);
						System.out.println(method);
						System.out.println(obj);
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						System.out.println("Error on method " + methodName);
						e.printStackTrace();
					}
				}
			}
		}
	}
}
