package com.founderz.chaincode;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;

/**
 * <p>FounderzAgreementChainCode 클래스는 Hyperledger Fabric 체인코드(스마트 컨트랙트)의 일환으로, 계약서를 블록체인 상에 저장하고 조회하는 기능을 제공합니다.
 * 해당 클래스는 ContractInterface를 구현하여, 체인코드의 주요 기능인 mint와 fetch를 포함합니다.</p>
 *
 * <p>체인코드는 기본적으로 계약서의 유무를 확인하고, 새로운 계약서를 블록체인 상에 저장하거나, 특정 키의 계약서를 조회하여 반환합니다.
 * </p>
 *
 * <ul>
 *      <li>{@code mint} 메서드는 주어진 키로 계약서가 이미 존재하는지 확인한 후, 존재하지 않는 경우 새로 계약서를 추가합니다.</li>
 *      <li>{@code fetch} 메서드는 주어진 키의 계약서를 블록체인 상에서 조회하고, 해당 계약서를 반환합니다.</li>
 * </ul>
 *
 * <p>체인코드가 호출될 때, Hyperledger Fabric의 {@code Context}와 {@code ChaincodeStub} 객체를 통해 블록체인 상태에 접근하여
 * 계약서를 저장하거나 조회할 수 있습니다.</p>
 *
 * <p>@Contract(name = "FounderzContract")</p>
 *
 * <p>기본 컨트랙트 이름은 FounderzContract이며, 이를 통해 컨트랙트 메서드가 호출될 수 있습니다.</p>
 *
 * @author seungwon
 */
@Default
@Contract(name = "FounderzContract")
class FounderzAgreementChainCode implements ContractInterface {

    /**
     * 계약서를 블록체인 상에 저장하는 메서드입니다.
     *
     * <p>주어진 키와 값이 인자로 전달되며, 해당 키가 이미 존재하지 않을 경우 계약서가 저장됩니다.
     * 키가 이미 존재하는 경우, ChaincodeException 예외가 발생합니다.</p>
     *
     * @param ctx 컨트랙트 실행 컨텍스트, 블록체인 상태와 상호작용할 수 있는 {@code Context} 객체입니다.
     * @param key 저장할 계약서의 키를 나타내는 문자열입니다.
     * @param value 저장할 계약서의 데이터를 나타내는 문자열입니다.
     * @return 계약서 저장 성공 시 "저장 완료" 메시지를 반환합니다.
     * @throws ChaincodeException 계약서가 이미 존재하는 경우 예외가 발생하며, 예외 메시지에 키 이름이 포함됩니다.
     */
    @Transaction(name = "mint")
    public String mint(final Context ctx, final String key, final String value) {
        final String assetState = ctx.getStub().getStringState(key);
        if (assetState != null && !assetState.isBlank()) {
            throw new ChaincodeException("key." + key + " Agreement already exists");
        }
        ctx.getStub().putStringState(key, value);
        return "저장 완료";
    }

    /**
     * 블록체인 상에 저장된 계약서를 조회하는 메서드입니다.
     *
     * <p>주어진 키에 해당하는 계약서를 조회하여 반환합니다. 계약서가 존재하지 않는 경우, ChaincodeException 예외가 발생합니다.</p>
     *
     * @param ctx 컨트랙트 실행 컨텍스트, 블록체인 상태와 상호작용할 수 있는 {@code Context} 객체입니다.
     * @param key 조회할 계약서의 키를 나타내는 문자열입니다.
     * @return 주어진 키에 해당하는 계약서의 데이터를 반환합니다.
     * @throws ChaincodeException 조회할 계약서가 존재하지 않는 경우 예외가 발생하며, 예외 메시지에 키 이름이 포함됩니다.
     */
    @Transaction(name = "fetch")
    public String fetch(final Context ctx, final String key) {
        final String state = ctx.getStub().getStringState(key);
        if (state == null || state.isBlank()) {
            throw new ChaincodeException("key." + key + " Agreement does not exist");
        }
        return state;
    }
}
