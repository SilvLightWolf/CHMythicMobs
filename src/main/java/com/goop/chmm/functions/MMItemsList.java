package com.goop.chmm.functions;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.items.MythicItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@api
public class MMItemsList extends AbstractFunction {
	@Override
	public Class<? extends CREThrowable>[] thrown() {
		return new Class[]{};
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

		List<MythicItem> items = new ArrayList(MythicMobs.inst().getItemManager().getItems());
		Collections.sort(items);
		Iterator iitems = items.iterator();


		CArray itemsarray = new CArray(t);
		while(iitems.hasNext()) {
			MythicItem item = (MythicItem) iitems.next();
			itemsarray.push(new CString(item.getInternalName(), t), t);
		}

		return itemsarray;
	}

	@Override
	public Version since() {
		return new SimpleVersion(1, 0, 0);
	}

	@Override
	public String getName() {
		return "mm_items_list";
	}

	@Override
	public Integer[] numArgs() {
		return new Integer[]{ 0 };
	}

	@Override
	public String docs() {
		return "array () get MythicMobs Item List";
	}
}
