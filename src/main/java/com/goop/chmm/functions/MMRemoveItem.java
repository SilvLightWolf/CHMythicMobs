package com.goop.chmm.functions;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREIOException;
import com.laytonsmith.core.exceptions.CRE.CRENotFoundException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.items.ItemManager;
import io.lumine.xikage.mythicmobs.items.MythicItem;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@api
public class MMRemoveItem extends AbstractFunction {
	@Override
	public Class<? extends CREThrowable>[] thrown() {
		return new Class[]{
				CRENotFoundException.class,
				CREIOException.class
		};
	}

	@Override
	public boolean isRestricted() {
		return false;
	}

	@Override
	public Boolean runAsync() {
		return null;
	}

	@Override
	public Construct exec(Target t, Environment env, Construct... args) throws ConfigRuntimeException {

		ItemManager manager = MythicMobs.inst().getItemManager();
		ConcurrentHashMap<String, MythicItem> map = null;
		Field f = null;

		for(Field field : manager.getClass().getDeclaredFields()) {
			if(field.getName().equals("items")) {
				f = field;
			}
		}

		if(f == null) {
			throw new CRENotFoundException("Cannot found field items. Request to develoer", t);
		}

		try {
			f.setAccessible(true);
			map = (ConcurrentHashMap) f.get(manager);
			f.setAccessible(false);
		} catch (IllegalAccessException e) {
			return CBoolean.FALSE;
		}

		if(map == null) {
			throw new CRENotFoundException("Cannot found items collection. request to developer.", t);
		}

		List<String> keys = new ArrayList<>(map.keySet());
		if(!keys.contains(args[0].val())) {
			throw new CRENotFoundException("Unknown item : " + args[0].val(), t);
		}

		MythicItem mi = map.get(args[0].val());

		String path = MythicMobs.inst().getDataFolder() + File.separator + "items" + File.separator + mi.getFile();
		File file = new File(path);
		if(!file.exists()) {
			throw new CREIOException("Cannot found " +path, t);
		}

		map.remove(args[0].val());
		file.delete();

		return CBoolean.TRUE;
	}

	@Override
	public Version since() {
		return new SimpleVersion(1, 0, 0);
	}

	@Override
	public String getName() {
		return "mm_remove_item";
	}

	@Override
	public Integer[] numArgs() {
		return new Integer[]{ 1 };
	}

	@Override
	public String docs() {
		return "boolean (itemName) remove item [Cannot remove default items]";
	}
}
