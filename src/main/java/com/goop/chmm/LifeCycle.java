package com.goop.chmm;


import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;

@MSExtension("CHMythicMobs")
public class LifeCycle extends AbstractExtension {

	@Override
	public Version getVersion() {
		return new SimpleVersion(1, 0, 0);
	}

}
