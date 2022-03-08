const path = require('path');
const solc = require('solc');
const fs = require('fs-extra');

const buildPath = path.resolve(__dirname,'build');
fs.removeSync(buildPath); //remove files in this folder

const freelancePath = path.resolve(__dirname,'contracts','Freelance.sol');
const source = fs.readFileSync(freelancePath,'utf8');

const input = { 
    language:'Solidity',
    sources: {  
        'Freelance.sol': {  
            content: source,
        },
    },
    settings: { 
        outputSelection: {
            '*': {
                '*': ['*'],
            },
        },
    },
};

const output = JSON.parse(solc.compile(JSON.stringify(input))).contracts['Freelance.sol'];
console.log(output);
fs.ensureDirSync(buildPath); // checks if directory exists, otherwise it creates it.

for(let contract in output){
    console.log(contract);
    fs.outputJsonSync(
        path.resolve(buildPath,contract+'.json'),
        output[contract]
    );
}
