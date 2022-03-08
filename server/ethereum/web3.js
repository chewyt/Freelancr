import Web3 from "web3";

window.ethereum.request({ method: "eth_requestAccounts" });
const web3 = new Web3(window.ethereum)
// console.log('Connected to MetaMask version ',web3.version)
// web3.eth.getAccounts().then(console.log)

export default web3;