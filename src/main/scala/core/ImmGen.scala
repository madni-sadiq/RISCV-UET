/********************************************************************
* Filename :    Branch.scala
* Date     :    28-03-2020
* 
* Description:  Derived from mini and uses MuxLookup based   
*               implementation.  
*********************************************************************/


package riscv_uet

import chisel3._
import chisel3.util._

import CONSTANTS._

class ImmGenIO extends Bundle with Config {
  val inst = Input(UInt(XLEN.W))
  val sel  = Input(UInt(3.W))
  val out  = Output(UInt(XLEN.W))
}


class ImmGen extends Module with Config {
  val io = IO(new ImmGenIO)

  val Iimm = io.inst(31, 20).asSInt
  val Simm = Cat(io.inst(31, 25), io.inst(11,7)).asSInt
  val Bimm = Cat(io.inst(31), io.inst(7), io.inst(30, 25), io.inst(11, 8), 0.U(1.W)).asSInt
  val Uimm = Cat(io.inst(31, 12), 0.U(12.W)).asSInt
  val Jimm = Cat(io.inst(31), io.inst(19, 12), io.inst(20), io.inst(30, 25), io.inst(24, 21), 0.U(1.W)).asSInt
  val Zimm = io.inst(19, 15).zext

  io.out := MuxLookup(io.sel, Iimm & -2.S, 
    Seq(IMM_I -> Iimm, IMM_S -> Simm, IMM_B -> Bimm, IMM_U -> Uimm, IMM_J -> Jimm, IMM_Z -> Zimm)).asUInt
}

/*
class ImmGenMux extends ImmGen {
  val sign = Mux(io.sel === IMM_Z, 0.S, io.inst(31).asSInt)
  val b30_20 = Mux(io.sel === IMM_U, io.inst(30,20).asSInt, sign)
  val b19_12 = Mux(io.sel =/= IMM_U && io.sel =/= IMM_J, sign, io.inst(19,12).asSInt)
  val b11 = Mux(io.sel === IMM_U || io.sel === IMM_Z, 0.S,
            Mux(io.sel === IMM_J, io.inst(20).asSInt,
            Mux(io.sel === IMM_B, io.inst(7).asSInt, sign)))
  val b10_5 = Mux(io.sel === IMM_U || io.sel === IMM_Z, 0.U, io.inst(30,25))
  val b4_1 = Mux(io.sel === IMM_U, 0.U,
             Mux(io.sel === IMM_S || io.sel === IMM_B, io.inst(11,8),
             Mux(io.sel === IMM_Z, io.inst(19,16), io.inst(24,21))))
  val b0 = Mux(io.sel === IMM_S, io.inst(7),
           Mux(io.sel === IMM_I, io.inst(20),
           Mux(io.sel === IMM_Z, io.inst(15), 0.U)))

  io.out := Cat(sign, b30_20, b19_12, b11, b10_5, b4_1, b0).asSInt.asUInt
} */

