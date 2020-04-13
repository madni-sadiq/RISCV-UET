
This is a simple RISC-V 3-stage pipeline processor based on riscv mini from UCB and currently implements RV32I ISA based on User-level ISA Version 2.0 and the Machine-level ISA using Privileged Architecture Version 1.7. (Update for both is required) 
 
Current Implementation:

- 3 stage pipelined processor
- No structural hazards
- Data hazards are resolved using forward and stall
- No branch prediction
- Simple instruction and data memory integration for testing


### Generate verilog

Running "src/main/scala/ProcessorTile.scala" 


