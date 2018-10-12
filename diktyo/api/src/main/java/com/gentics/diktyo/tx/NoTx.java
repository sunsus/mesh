package com.gentics.diktyo.tx;

import com.gentics.diktyo.type.TypeManager;

public interface NoTx extends BaseTransaction {

	TypeManager type();
}
