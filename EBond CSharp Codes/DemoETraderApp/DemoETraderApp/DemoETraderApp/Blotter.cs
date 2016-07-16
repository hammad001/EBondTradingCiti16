using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DemoETraderApp
{
    class Blotter
    {
        //Amruta's blotter...
        public int OrderId { get; set; }
        public string ISIN { get; set; }
        public char BuySell { get; set; }
        public DateTime PurchaseDate { get; set; }
        public int FaceValue { get; set; }
        public int Quantity { get; set; }
        public string Currency { get; set; }
        public double CouponRate  { get; set; }
        public char CouponFrequency { get; set; }
        public DateTime MaturityDate { get; set; }
        public double Yeild { get; set; }

        public Blotter(int OrderId, string ISIN,char BuySell, DateTime PurchaseDate,int FaceValue, int Quantity,
           string Currency, double CouponRate,char CouponFrequency,DateTime MaturityDate, double Yeild)
        {
            this.OrderId = OrderId;
            this.ISIN = ISIN;
            this.BuySell = BuySell;
            this.PurchaseDate = PurchaseDate;
            this.FaceValue = FaceValue;
            this.Quantity = Quantity;
            this.Currency = Currency;
            this.CouponRate = CouponRate;
            this.CouponFrequency = CouponFrequency;
            this.MaturityDate = MaturityDate;
            this.Yeild = Yeild;
            
        }


    }
}
