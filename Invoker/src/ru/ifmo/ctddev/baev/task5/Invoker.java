package ru.ifmo.ctddev.baev.task5;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Invoker {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Error! Enter more than 1 argument");
			return;
		}

		String className = args[0];
		Class<?> c = null;
		ClassLoader cl;

		try {
			URL path = new URL("file://.");
			cl = new URLClassLoader(new URL[] { path });
			c = cl.loadClass(className);
		} catch (ClassNotFoundException e) {
			System.out.println("Error! Class not found");
			return;
		} catch (MalformedURLException e) {

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
		} catch (InstantiationException e) {
			System.out.println("Error! can't instance");
			e.printStackTrace();
			return;
		} catch (IllegalAccessException e) {
			System.out.println("Error! class or its nullary constructor is not accessible");
			e.printStackTrace();
			return;
		}

		Object[] args2 = Arrays.copyOfRange(args, 2, args.length);
		String methodName = args[1];
		Set<String> s = new HashSet<String>();
		for (Method method : c.getMethods()) {
			if (method.getName().equals(methodName)) {
				boolean wasError = false;
				Class<?>[] methodArgs = method.getParameterTypes();
				if (methodArgs.length != args2.length) {
					continue;
				}
				for (Class<?> curArg : methodArgs) {
					if (!curArg.isAssignableFrom(String.class)) {
						wasError = true;
						break;
					}
				}
				if (!wasError && !s.contains(Arrays.toString(methodArgs))) {
					try {
						method.invoke(obj, args2);
						s.add(Arrays.toString(methodArgs));
						System.out.println(method);
						System.out.println(obj);
					} catch (IllegalAccessException e) {
						System.out.println("Error on method " + methodName + ": method is inaccessible");
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						System.out.println("Error on method " + methodName);
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						System.out.println("Error on method " + methodName + ": method throws an exception");
						e.printStackTrace();
					}
				}
			}
		}
	}
}
