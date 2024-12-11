package zy.holder;

import android.content.Context;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

final class HolderUtils {

    private static final int CACHE_COUNT = 32;

    private static final LruCache<String, Class<? extends ViewBinding>> CACHE = new LruCache<>(CACHE_COUNT);

    private static final LruCache<Class<? extends ViewBinding>, Method> CACHE_METHODS = new LruCache<>(CACHE_COUNT);

    static ViewBinding binding(Class<? extends ViewBinding> clazz, Context context, ViewGroup viewGroup) {
        try {
            Method inflate;
            if (CACHE_METHODS.get(clazz) != null) {
                inflate = CACHE_METHODS.get(clazz);
            } else {
                inflate = clazz.getMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
                CACHE_METHODS.put(clazz, inflate);
            }
            return (ViewBinding) inflate.invoke(null, LayoutInflater.from(context), viewGroup, false);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    static Class<? extends ViewBinding> findBindingClass(Type type) {
        if (type == null) {
            throw new NullPointerException("Type must not be null");
        }
        String typeStr = type.toString();
        if (CACHE.get(typeStr) != null) {
            return CACHE.get(typeStr);
        }
        if (type instanceof Class) {
            if (Modifier.isAbstract(((Class) type).getModifiers())) {
                throw new IllegalArgumentException("class is abstract : " + type);
            }
            return findBindingClass(((Class) type).getGenericSuperclass());
        }
        if (type instanceof ParameterizedType) {
            Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
            if (arguments.length == 0) {
                return findBindingClass(((ParameterizedType) type).getRawType());
            }
            for (Type argument : arguments) {
                if (argument instanceof Class && ViewBinding.class.isAssignableFrom((Class<?>) argument)) {
                    CACHE.put(typeStr, (Class<? extends ViewBinding>) argument);
                    return (Class<? extends ViewBinding>) argument;
                }
            }
        }
        throw new IllegalArgumentException("unknown type : " + type);
    }

}
