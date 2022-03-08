import web3 from "./web3";
import ContractFactory from "./build/ContractFactory.json";
export const address = "0x0d515E4560FEa52E209b022723B56826f6a18409";

export const abi = ContractFactory.abi;
// [
//     {
//       inputs: [ [Object] ],
//       name: 'createContract',
//       outputs: [],
//       stateMutability: 'nonpayable',
//       type: 'function',
//       constant: undefined,
//       payable: undefined,
//       signature: '0x9db8d7d5'
//     },
//     {
//       inputs: [ [Object] ],
//       name: 'deployedContracts',
//       outputs: [ [Object] ],
//       stateMutability: 'view',
//       type: 'function',
//       constant: true,
//       payable: undefined,
//       signature: '0x9ad1ee10'
//     },
//     {
//       inputs: [],
//       name: 'getDeployedContracts',
//       outputs: [ [Object] ],
//       stateMutability: 'view',
//       type: 'function',
//       constant: true,
//       payable: undefined,
//       signature: '0xaa9a068f'
//     }
//   ]

export default new web3.eth.Contract(abi, address);
