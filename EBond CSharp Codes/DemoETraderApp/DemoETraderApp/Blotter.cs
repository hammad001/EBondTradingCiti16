using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using System.IO;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Json;
using System.Windows;

namespace DemoETraderApp
{
    [DataContract]
    public class Blotter
    {
        //Amruta's blotter...
        [DataMember]
        public int OrderId { get; set; }
        //[DataMember]
        //public string ISIN { get; set; }
        [DataMember]
        public char BuySell { get; set; }

        [DataMember]
        public int Quantity { get; set; }

        [DataMember]
        public string PurchaseDate { get; set; }
        //[DataMember]
        //public int FaceValue { get; set; }
        
        //[DataMember]
        //public string Currency { get; set; }
        //[DataMember]
        //public double CouponRate { get; set; }
        //[DataMember]
        //public char CouponFrequency { get; set; }
        //[DataMember]
        //public string MaturityDate { get; set; }
        //[DataMember]
        //public double Yeild { get; set; }
        //[DataMember]
        //public string purchasedPrice { get; set; }
        [DataMember]
        public EBond ebond { get; set; }
        

        public Blotter(int OrderId,  char BuySell, int Quantity, string PurchaseDate,   EBond ebond
            )
        {
            this.OrderId = OrderId;
           // this.ISIN = ISIN;
            this.BuySell = BuySell;
            this.PurchaseDate = PurchaseDate;
           // this.FaceValue = FaceValue;
            this.Quantity = Quantity;
            //this.Currency = Currency;
            //this.CouponRate = CouponRate;
            //this.CouponFrequency = CouponFrequency;
            //this.MaturityDate = MaturityDate;
            //this.Yeild = Yeild;
            // this.purchasedPrice = purchasedPrice;
            this.ebond = ebond;

        }

        public Blotter()
        {

        }
                




        }
    }

