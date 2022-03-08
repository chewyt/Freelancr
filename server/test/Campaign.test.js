const assert = require('assert');
const ganache = require('ganache-cli');
const Web3 = require('web3');
const web3 =new Web3(ganache.provider({ gasLimit: 10000000 }));

const compiledFactory =  require('../ethereum/build/ContractFactory.json');
const compiledFreelancer =  require('../ethereum/build/freelancer.json');

let accounts;
let factory;
let freelancer;
let freelancerAddress;

beforeEach(async()=>{
    accounts = await web3.eth.getAccounts();
    factory = await new web3.eth.Contract(compiledFactory.abi)
        .deploy({data:compiledFactory.evm.bytecode.object})
        .send({from: accounts[1], gas: '5000000'});

    await factory.methods.createContract('1000000000').send({
        from:accounts[0],
        gas:'2000000'
    });

    const addresses = await factory.methods.getDeployedContracts().call();
    freelancerAddress = addresses[0];

    freelancer = await new web3.eth.Contract(compiledFreelancer.abi,freelancerAddress);


})

describe('Freelancer contract validation',()=>{

    it('deploys a factory and a contract',()=>{
        assert.ok(factory.options.address);
        assert.ok(freelancer.options.address);
        // assert(true);
    });

    it('marks caller as the campaign manager',async()=>{
        const client = await freelancer.methods.clientAddress().call();
        assert.equal(accounts[0],client);
    });

    it('able to initialize project successfully with automated contribution requirements',async()=>{
        
        console.log("GOGOGOGO");
        await freelancer.methods.initialize().send({
            value:'1000000000',
            from:accounts[1],
            gas:'9000000'
        });
        const budget = await freelancer.methods.getBalance().call();
        console.log("budget>>>>>",budget);
        
        assert.equal(budget,1000000000);
    });
    it('requires that startTask is not allowed by client',async()=>{
        await freelancer.methods.initialize().send({
            value:'1000000000',
            from:accounts[1]
        });
        try {
            await freelancer.methods.startTask(0).call({
                from: accounts[1]
            })
            assert(false);
        } catch (error) {
            assert(error);
        }
    });

    it('requires that startTask is not allowed before initialization',async()=>{
        try {
            await freelancer.methods.startTask(0).call({
                from: accounts[2]
            })
            assert(false);
        } catch (error) {
            assert(error);
        }
    });

    it('allows the client only to approve project after task completion', async()=>{
        
        await freelancer.methods.initialize().send({
            value:'1000000000',
            from:accounts[1]
        });
        try {
            await freelancer.methods.startTask(0).call({
                from: accounts[2]
            })
            
        } catch (error) {
            assert(false);
        }

        try {
            await freelancer.methods.approveTask(0).call({
                from: accounts[1]
            })
            assert(true);
            
        } catch (error) {
            assert(false);
        }
        
    });

    it('do not allow funds transfer to take place without task approval',async()=>{
        
        await freelancer.methods.initialize().send({
            value:'1000000000',
            from:accounts[1]
        });
        try {
            await freelancer.methods.startTask(0).call({
                from: accounts[2]
            })
            
        } catch (error) {
            assert(false);
        }

        try {
            await freelancer.methods.releaseFunds(0).call({
                from: accounts[2]
            })
            assert(false);
            
        } catch (error) {
            assert(error);
        }
   
    });

    it('do not allow funds transfer to other party except freelancer after task approval',async()=>{
        
        await freelancer.methods.initialize().send({
            value:'1000000000',
            from:accounts[1]
        });
        try {
            await freelancer.methods.startTask(0).call({
                from: accounts[2]
            })
            
        } catch (error) {
            assert(false);
        }

        try {
            await freelancer.methods.approveTask(0).call({
                from: accounts[1]
            })
            
            
        } catch (error) {
            assert(false);
        }

        try {
            await freelancer.methods.releaseFunds(0).call({
                from: accounts[3]
            })
            assert(false);
            
        } catch (error) {
            assert(error);
        }
   
    });

    it('processes correct amount of funds transfer',async()=>{
        
        await freelancer.methods.initialize().send({
            value:'1000000000',
            from:accounts[1]
        });
        try {
            await freelancer.methods.startTask(0).call({
                from: accounts[2]
            })
            
        } catch (error) {
            assert(false);
        }

        try {
            await freelancer.methods.approveTask(0).call({
                from: accounts[1]
            })
            
            
        } catch (error) {
            assert(false);
        }

        try {
            await freelancer.methods.releaseFunds(0).send({
                from: accounts[2]
            })
            
        } catch (error) {
            assert(false);
        }
   
        let balance = await web3.eth.getBalance(accounts[2]);
        balance=web3.utils.fromWei(balance,'ether');
        balance=parseFloat(balance);
        console.log('Final balance at accounts[2]: ',balance);
        assert(balance>100);        
    });



});