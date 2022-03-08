const HDWalletProvider  =require('@truffle/hdwallet-provider');
const Web3 = require('web3');

const compiledFactory = require('./build/ContractFactory.json');

const provider  = new HDWalletProvider(
    process.env.MNEMONIC,
    'https://rinkeby.infura.io/v3/83743cc2091340f1a2baa688aa271a95'
)

const web3 = new Web3(provider);
const deploy = async()=>{

    const accounts  = await web3.eth.getAccounts();
    console.log('Attempting to deploy from account ', accounts[0]);

    //Contract creation
    const result = await new web3.eth.Contract(compiledFactory.abi)
        .deploy({data:compiledFactory.evm.bytecode.object})
        .send({from: accounts[0], gas:'7000000'});

    // console.log(JSON.stringify(abi));
    console.log('Contract deployed to', result.options.address);
    console.log(compiledFactory.abi);
    provider.engine.stop();

}

deploy();