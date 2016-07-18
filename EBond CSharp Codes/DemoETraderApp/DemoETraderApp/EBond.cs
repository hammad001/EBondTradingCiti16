using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using System.IO;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Json;

namespace DemoETraderApp
{
    [DataContract]
<<<<<<< HEAD
   public class EBond
=======
    public class EBond
>>>>>>> refs/remotes/origin/Amruta
    {
        [DataMember]
        public int bondId { get; set; }
        [DataMember]
        public string isin { get; set; }
        [DataMember]
        public string issueDate { get; set; }
        [DataMember]
<<<<<<< HEAD
        public int settlementDays { get; set; }
=======
        public string settlementDays { get; set; }
>>>>>>> refs/remotes/origin/Amruta
        [DataMember]
        public int faceValue { get; set; }
        [DataMember]
        public double couponRate { get; set; }
        [DataMember]
        public char couponFrequency { get; set; }
        [DataMember]
        public string maturityDate { get; set; }
        [DataMember]
        public double lastPrice { get; set; }
        [DataMember]
        public double high { get; set; }
        [DataMember]
        public double low { get; set; }
        [DataMember]
        public double changeInPrice { get; set; }
        [DataMember]
        public double yeild { get; set; }
        [DataMember]
        public string creditRating { get; set; }
        [DataMember]
        public string currency { get; set; }

<<<<<<< HEAD
        public EBond(string isin, string issueDate, int settlementDays, int faceValue, double couponRate,
            char couponFrequency, string maturityDate, double lastPrice, double high, double low, double changeInPrice,
            double yeild, string creditRating, string currency)
        {
            
=======
        public EBond(string isin, string issueDate, string settlementDays, int faceValue, double couponRate,
            char couponFrequency, string maturityDate, double lastPrice, double high, double low, double changeInPrice,
            double yeild, string creditRating, string currency)
        {

>>>>>>> refs/remotes/origin/Amruta
            this.isin = isin;
            this.issueDate = issueDate;
            this.settlementDays = settlementDays;
            this.faceValue = faceValue;
            this.couponRate = couponRate;
            this.couponFrequency = couponFrequency;
            this.maturityDate = maturityDate;
            this.lastPrice = lastPrice;
            this.high = high;
            this.low = low;
            this.changeInPrice = changeInPrice;
            this.yeild = yeild;
            this.creditRating = creditRating;
            this.currency = currency;
        }

        public override string ToString()
        {
            return string.Format(isin);
        }

        public EBond()
        {

        }
    }
}
