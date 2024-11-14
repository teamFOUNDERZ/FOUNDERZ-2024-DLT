package com.founderz.chaincode;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;

@Default
@Contract(name = "FounderzContract")
class FounderzAgreementChainCode implements ContractInterface {

    @Transaction(name = "mint")
    public String mint(final Context ctx, final String key, final String value) {
        final String assetState = ctx.getStub().getStringState(key);
        if (assetState != null && !assetState.isBlank()) {
            throw new ChaincodeException("key." + key + " Agreement already exists");
        }
        ctx.getStub().putStringState(key, value);
        return "저장 완료";
    }
    
    @Transaction(name = "fetch")
    public String fetch(final Context ctx, final String key) {
        final String state = ctx.getStub().getStringState(key);
        if (state == null || state.isBlank()) {
            throw new ChaincodeException("key." + key + " Agreement does not exist");
        }
        return state;
    }
}
