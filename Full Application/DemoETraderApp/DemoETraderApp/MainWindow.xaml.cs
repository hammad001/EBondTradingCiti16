using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.IO;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Json;
using System.Web.Script.Serialization;

namespace DemoETraderApp
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    /// 
   
    public partial class MainWindow : Window
    {
        public int accountId;
        public string name;
        public MainWindow()
        {
            InitializeComponent();
        }

        public MainWindow(int accountId, string name)
        {
            InitializeComponent();
            this.name = name;
            this.accountId = accountId;
        }

//        public const string IpAddress= "http://192.168.137.37:8080";
        public const string IpAddress = "http://192.168.137.1:8080";

        private void ShowBSM(object sender, RoutedEventArgs e)
        {
           // Show();
        }

        public List<EBond> EBondList = new List<EBond>();
        List<EBond> BondList = new List<EBond>();

        private List<EBond> bondsetup(EBond obj)
        {
            BondList = new List<EBond>();
            
            BondList.Add(obj);
            return BondList;
        }
        private void selectbond(object sender, RoutedEventArgs e)
        {
            //int index = Bondslstbx.SelectedIndex;
            
            if (dataGridfrbonds.SelectedIndex == -1)
            {
                MessageBox.Show("Please select a bond");               
            }
            else
            {
                EBond sentebond = (EBond)dataGridfrbonds.SelectedItem;
              // dataGridfrbonds.ItemsSource= bondsetup(sentebond);
                //dataGridfrbonds.Items.Add(sentebond);
                GridfrBond.IsEnabled = true;
                buttonfrBT.IsEnabled = true;
                Idatetxt.Text =sentebond.issueDate;
                Sdaystxt.Text = sentebond.settlementDays.ToString();
                Fvaltxt.Text = sentebond.faceValue.ToString();
                Hightxt.Text = sentebond.high.ToString();
                Lowtxt.Text = sentebond.low.ToString();
                CiPricetxt.Text = sentebond.changeInPrice.ToString();

            }

            
        }
        public int GetIndex()
        {
            //int selectedBondIndex = Bondslstbx.SelectedIndex;
            string searchItem = dataGridfrbonds.SelectedItem.ToString();
            int index = 0;
            foreach(var item in ISINcbx.Items)
            {
                if (item.ToString() == searchItem) return index;
                index++;
            }
            return -1;
        }
        private void OpenTBWTab(object sender, RoutedEventArgs e)
        {
            
            EBond bookingbond = (EBond)dataGridfrbonds.SelectedItem;
            tabControl.SelectedItem = TBWTab;
            ISINcbx.SelectedItem = bookingbond;
            
            ISINcbx.SelectedIndex = GetIndex() ;            
            FVtxt.Text = bookingbond.faceValue.ToString();
            CRtxt.Text = bookingbond.couponRate.ToString();
            CFtxt.Text = bookingbond.couponFrequency.ToString();
            CrRtxt.Text = bookingbond.creditRating.ToString();
            LPtxt.Text = bookingbond.lastPrice.ToString();
            MDtxt.Text = bookingbond.maturityDate.ToString();
            SDtxt.Text = SettlementDate(bookingbond.settlementDays);
            
            
                                    
        }
        public string SettlementDate (int days)
        {
            DateTime Settlementdate = new DateTime();
            Settlementdate = DateTime.Today.AddDays(days);
            return Settlementdate.ToShortDateString();
        }

        private void BondListSearch(object sender, RoutedEventArgs e)
        {
            // Bondlist Setup
            // string msg = "{\"isin\":\"IN126\",\"bondName\":\"saleem\"}";
            // MessageBox.Show(CRfrom.Text);
            // to remove defaults entries in txt boxes
            if (txtbxisin.Text == "Enter Isin") txtbxisin.Text = "";
            if (CRfrom.Text == "From") CRfrom.Text = "";
            if (CRto.Text == "To") CRto.Text = "";
            if (txtbxYieldfrm.Text == "From") txtbxYieldfrm.Text = "";
            if (yeildTo.Text == "To") yeildTo.Text = "";
            if (LPfrom.Text == "From") LPfrom.Text = "";
            if (LPto.Text == "To") LPto.Text = "";
            if (Currencybx.SelectedIndex == 0) Currencybx.SelectedIndex=5 ;
            if (comboBoxFrequency.SelectedIndex == 0) comboBoxFrequency.SelectedIndex = 4;
            if (CreditRatingBx.SelectedIndex == 0) CreditRatingBx.SelectedIndex = 10;
            // to remove defaults
           
            string mdto = MDto.SelectedDate.Value.ToShortDateString();
            string searchstring = "{\"isin\":\"" + txtbxisin.Text + "\",\"couponRateFrom\":\"" + CRfrom.Text +
                "\",\"couponRateTo\":\"" + CRto.Text + "\",\"frequency\":\"" + comboBoxFrequency.SelectedItem.ToString() +
                "\",\"currency\":\"" + Currencybx.SelectedItem.ToString() + "\",\"creditRating\":\"" + CreditRatingBx.SelectedItem.ToString() +
                "\",\"yeildFrom\":\"" + txtbxYieldfrm.Text+ "\",\"yeildTo\":\"" + yeildTo.Text + "\",\"lastPriceFrom\":\"" +
                LPfrom.Text+ "\",\"lastPriceTo\":\"" + LPto.Text + "\",\"maturityDateFrom\":\"" +MDfrom.SelectedDate.Value.ToShortDateString()
                + "\",\"maturityDateTo\":\"" + mdto + "\"}";
            // MessageBox.Show(searchstring);
            var cli = new WebClient();
            cli.Proxy = null;
            cli.Headers[HttpRequestHeader.ContentType] = "text/plain";
            string send = null;
            try {
                send = cli.UploadString(IpAddress + "/EBondTraderWeb/rest/bond/bsm", searchstring);
            }catch(System.Net.WebException exc)
            {
                MessageBox.Show("Unable to connect to remote server");
            }
            //MessageBox.Show(send);
                        
            Stream Ebondstream = StringToStream(send);
            StreamReader reader = new StreamReader(Ebondstream);
            DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(EBond[]));
            EBond[] EBonds = (EBond[])serializer.ReadObject(Ebondstream);
            //string help = "";
            //dataGridfrbonds.Items.Clear();
            dataGridfrbonds.ItemsSource = null;
            if (EBonds.Length == 0) MessageBox.Show("Sorry, There are no bonds with the entered criteria..");
            dataGridfrbonds.ItemsSource = EBonds;
            //foreach(EBond ebond in EBonds)
            //{
            //    Bondslstbx.Items.Add(ebond);
            //    // help += ebond.ToString()+ "\n";
               
            //}
           // MessageBox.Show(help);
           
        }

        private void Setup(object sender, RoutedEventArgs e)
        {
            CreditRatingBx.Items.Add("  --Select--");
            CreditRatingBx.Items.Add("AAA");
            CreditRatingBx.Items.Add("AA");
            CreditRatingBx.Items.Add("A");
            CreditRatingBx.Items.Add("BBB");
            CreditRatingBx.Items.Add("BB");
            CreditRatingBx.Items.Add("B");
            CreditRatingBx.Items.Add("CCC");
            CreditRatingBx.Items.Add("CC");
            CreditRatingBx.Items.Add("C");
            CreditRatingBx.Items.Add("");
            comboBoxFrequency.Items.Add("  --Select--");
            comboBoxFrequency.Items.Add("A");
            comboBoxFrequency.Items.Add("S");
            comboBoxFrequency.Items.Add("Q");
            comboBoxFrequency.Items.Add("");
            Currencybx.Items.Add(" --Select--");
            Currencybx.Items.Add("USD");
            Currencybx.Items.Add("INR");
            Currencybx.Items.Add("GBP");
            Currencybx.Items.Add("EUR");
            Currencybx.Items.Add("");

            //username setup
            labelhello.Content = "Hello " + name;
            //setup of ISINs..
            var client = new WebClient();
            client.Proxy = null;
            string isinString = client.DownloadString( IpAddress + "/EBondTraderWeb/rest/bond/tbs");
                       
            JavaScriptSerializer js = new JavaScriptSerializer();
            List<EBond> EBondslst = (List<EBond>)js.Deserialize(isinString, typeof(List<EBond>));

            foreach (EBond ebond in EBondslst)
            {
                ISINcbx.Items.Add(ebond);

            }

        }
        public Stream StringToStream(string s)
        {
            MemoryStream stream = new MemoryStream();
            StreamWriter writer = new StreamWriter(stream);
            writer.Write(s);
            writer.Flush();
            stream.Position = 0;
            return stream;

        }

                
        private void ClearSearch(object sender, RoutedEventArgs e)
        {
            CreditRatingBx.SelectedIndex = 0;
            Currencybx.SelectedIndex = 0;
            comboBoxFrequency.SelectedIndex = 0;
            txtbxisin.Text = "";
            CRfrom.Text = "";
            CRto.Text = "";
            txtbxYieldfrm.Text = "";
            yeildTo.Text = "";
            LPfrom.Text = "";
            LPto.Text = "";
            MDfrom.SelectedDate = new DateTime(2000,07,16);
            MDto.SelectedDate = new DateTime(2051, 07, 16);
            dataGridfrbonds.ItemsSource=null;
        }

        private void ReselectBond(object sender, MouseButtonEventArgs e)
        {
            string nwisin = ISINcbx.SelectedItem.ToString();
            
            var user = new WebClient();
            user.Proxy = null;
            string address = IpAddress + "/EBondTraderWeb/rest/bond/tbs?isin=" + nwisin;
            string searchedbond = user.DownloadString(address);
            Stream bondstream = StringToStream(searchedbond);
            StreamReader reader = new StreamReader(bondstream);
            DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(EBond[]));
            EBond[] EbondwithNwIsin = (EBond[])serializer.ReadObject(bondstream);
            EBond NwIsin = EbondwithNwIsin[0];

            FVtxt.Text = NwIsin.faceValue.ToString();
            CRtxt.Text = NwIsin.couponRate.ToString();
            CFtxt.Text = NwIsin.couponFrequency.ToString();
            CrRtxt.Text = NwIsin.creditRating.ToString();
            LPtxt.Text = NwIsin.lastPrice.ToString();
            DateTime mddate = DateTime.Parse(NwIsin.maturityDate);

            MDtxt.Text = mddate.ToShortDateString();
            SDtxt.Text = SettlementDate(NwIsin.settlementDays);

        }

       

        public double CalculateSetlmntAmnt(double lastPice,int quantity)
        {
            return lastPice * quantity;
        }
        public int GetFreq()
        {
            int r = 1;
            if (CFtxt.Text == "S") r = 2;
            else if (CFtxt.Text == "Q") r = 4;

            return r;
        }
        public int Getyrs(string date)
        {
            DateTime md = DateTime.Parse(date);
            int yr=(md-DateTime.Today).Days/365;
            return yr;
        }
        public double CalculateCleanPrice()
        {
            double couponrate = (double.Parse(CRtxt.Text))/100;
            double FaceVal = double.Parse(FVtxt.Text);
            double C = couponrate * FaceVal;
            int k = GetFreq();
            double RbyK = (double.Parse(Yieldtxt.Text)) /k;
            int n = Getyrs(MDtxt.Text);
            
            double exp = (1 - ((1) /( Math.Pow((1 + RbyK) , (k * n))))) * (C / RbyK);
            double exp2 = (FaceVal) * ((1) / (Math.Pow((1 + RbyK), (k * n))));
            double CP = exp + exp2;
            return CP;

        }
        public int calcdatediff(DateTime issdDate)
        {
            int diff = (DateTime.Today.Date - issdDate).Days;
            
            return (diff%365);
            
        }
        // DateTime x = new DateTime();
        double lastPice;
        int quantity;
        string bondid;
        public double CalculateAcrdAmnt()
        {
            double couponrate = (double.Parse(CRtxt.Text))/100;
            double FaceVal = double.Parse(FVtxt.Text);
            double C = couponrate * FaceVal;
            EBond it=(EBond)ISINcbx.SelectedItem;
            lastPice = it.lastPrice;
            quantity = int.Parse(Quantitybx.Text);
            bondid = it.bondId.ToString();
            DateTime isd = DateTime.Parse(it.issueDate);
            int issuedays = calcdatediff(isd);
            int res1 = (issuedays) % (365 / GetFreq());
            double res = (res1 / ((double)365 / GetFreq()))*C;
            return res;
        }
        public double CalDirtyPrice()
        {
            double ret;
            ret = CalculateCleanPrice() + CalculateAcrdAmnt();
            return ret;
        }

        private void showDefaultTxt(object sender, RoutedEventArgs e)
        {

            CPtxt.Text = "";
        }

        private void setupAmounts(object sender, KeyEventArgs e)
        {
            if(e.Key == Key.Enter)
            {
                string Cp = CalculateCleanPrice().ToString();
                CPtxt.Text = Cp;
                string Dp = CalDirtyPrice().ToString();
                DPtxt.Text = Dp;
                string AA = CalculateAcrdAmnt().ToString();
                AAtxt.Text = AA;
                string Sa = CalculateSetlmntAmnt(lastPice, quantity).ToString();
                SAtxt.Text = Sa;
            }
        }

        private void BuyTrade(object sender, RoutedEventArgs e)
        {
            if (Quantitybx.Text == "") MessageBox.Show("Please enter the quantity in the field");
            else
            {
                var client = new WebClient();
                client.Proxy = null;
                client.Headers[HttpRequestHeader.ContentType] = "text/plain";
                EBond it = (EBond)ISINcbx.SelectedItem;
                lastPice = it.lastPrice;
                quantity = int.Parse(Quantitybx.Text);
                bondid = it.bondId.ToString();
                string post = "{" + "\"quantity\":\"" + Quantitybx.Text + "\",\"bondId\":\"" + bondid +
                    "\",\"buySell\":\"B\",\"accountId\":\"" + accountId.ToString() + "\"}";
                // MessageBox.Show(post);

//                string rec = client.UploadString( IpAddress + "/EBondTraderWeb/rest/bond/tbs", post);
                string rec = client.UploadString("http://192.168.137.1:8080/EBondTraderWeb/rest/bond/tbs", post);

                // MessageBox.Show(rec);
                if (rec == "{\"status\":\"Success\"}") MessageBox.Show("$$Your E-Bond has been successfully booked!$$");
                else MessageBox.Show("**Sorry, Your Bond has not been booked**");
            }
        }

        private void SellTrade(object sender, RoutedEventArgs e)
        {
            if (Quantitybx.Text == "" ) MessageBox.Show("Please enter the quantity in the field");
            else
            {
                var client = new WebClient();
                client.Proxy = null;
                client.Headers[HttpRequestHeader.ContentType] = "text/plain";
                string post = "{" + "\"quantity\":\"" + Quantitybx.Text + "\",\"bondId\":\"" + bondid +
                    "\",\"buySell\":\"S\",\"accountId\":\""+accountId.ToString()+"\"}";
//                string rec = client.UploadString( IpAddress + "/EBondTraderWeb/rest/bond/tbs", post);
                string rec = client.UploadString("http://192.168.137.1:8080/EBondTraderWeb/rest/bond/tbs", post);
                //                string blotter_populate = clie.DownloadString("http://192.168.137.1:8080/EBondTraderWeb/rest/bond/blotter/" + accountId.ToString());
                //MessageBox.Show(rec);
                if (rec == "{\"status\":\"Success\"}") MessageBox.Show("$$Your E-Bond has been successfully Sold!$$");
                else MessageBox.Show("**Sorry, Your Bond has not been Sold**");
            }
        }

        private void ISIN(object sender, RoutedEventArgs e)
        {
            txtbxisin.Text = "";
        }

        private void CR(object sender, RoutedEventArgs e)
        {
            CRfrom.Text = "";
        }

        private void CRGS(object sender, RoutedEventArgs e)
        {
            CRto.Text = "";
        }

        private void Yield(object sender, RoutedEventArgs e)
        {
            txtbxYieldfrm.Text = "";
        }

        private void Yieldto(object sender, RoutedEventArgs e)
        {
            yeildTo.Text = "";
        }

        private void LP(object sender, RoutedEventArgs e)
        {
            LPfrom.Text = "";
        }

        private void LPtoGs(object sender, RoutedEventArgs e)
        {
            LPto.Text = "";
        }

        //OtherTabCodes//               

        public string response;

        List<Blotter> populatedListBlotter = new List<Blotter>();
        List<Blotter> context = new List<Blotter>();
        int pageSize = 15;
        private void test(object sender, RoutedEventArgs e)
        {
            context = new List<Blotter>();
            populatedListBlotter = new List<Blotter>();
            //populatedListBlotter.Clear();
            //MessageBox.Show("Test");
            var clie = new WebClient();
            clie.Proxy = null;
            // cli.Headers[HttpRequestHeader.ContentType] = "application/json";
            //            string blotter_populate = clie.DownloadString( IpAddress + "/EBondTraderWeb/rest/bond/blotter/"+accountId.ToString());
            string blotter_populate = clie.DownloadString("http://192.168.137.1:8080/EBondTraderWeb/rest/bond/blotter/" + accountId.ToString());
//            string blotter_populate = clie.DownloadString("http://192.168.137.1:8080/EBondTraderWeb/rest/bond/blotter/5");
            //MessageBox.Show(blotter_populate);
            JavaScriptSerializer js = new JavaScriptSerializer();
            context = (List<Blotter>)js.Deserialize(blotter_populate, typeof(List<Blotter>));

            for (int i = 0; i < pageSize; i++)
            {
                try { 
                populatedListBlotter.Add(context[i]);//fetchFrom to fetchFrom+pageSize
                }
                    catch(Exception exc) { continue; }
            }
            datagridblotter.ItemsSource = populatedListBlotter;
            pageSlider.Maximum = context.Count() / pageSize;
            PageVal.Text = "1 of " + pageSlider.Maximum + 1;
            pageSlider.Value = 0;
            recFrom.Text = "From 1";
            recTo.Text = "To 15";
            //datagridblotter.ItemsSource = context;
            //populate comboboxfrIsins
            foreach (Blotter bookedBond in context)
            {
                comboBoxfrIsin.Items.Add(bookedBond);
                bookedBond.SetStatus();
            }
            //  comboBoxfrIsin.Items.Add("");

        }

        private void pageSlider_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            //int recTotal = Convert.ToInt16(numRecords.Text);
            int recTotal = context.Count;
            //MessageBox.Show(""+recTotal);
            //int pageSize = 10;
            int numPages = 0;//total number of pages
            if (recTotal % pageSize == 0)
            {
                numPages = (recTotal / pageSize);//no remainder records, hence last page not needed
            }
            else
            {
                numPages = (recTotal / pageSize) + 1;// +1 for remainder records
            }

            pageSlider.Maximum = numPages - 1;//slider starts from 0, not 1
            int pageNum = Convert.ToInt16(pageSlider.Value) + 1;//pageNum is not 0
            PageVal.Text = "" + pageNum +" of "+numPages;//display page Number
            int fetchFrom = (pageSize * (pageNum - 1) + 1);
            int fetchTo = 0;//assigned below

            if (pageNum * pageSize > recTotal)//when we're on last page
            {
                fetchTo = recTotal;
            }
            else
            {
                fetchTo = pageNum * pageSize;
            }
            recFrom.Text = "From " + fetchFrom;
            recTo.Text = "To " + fetchTo;

            populatedListBlotter = new List<Blotter>();
            //MessageBox.Show(""+fullList[4].entry);
            for (int i = 0; i < pageSize; i++)
            {
                /*if (context[fetchFrom + i - 1] == null)
                {
                    Blotter tempBlotter = new Blotter();
                    tempBlotter.PurchaseDate="";
                    //continue;
                    populatedListBlotter.Add(tempBlotter);
                }*/
                try {
                    populatedListBlotter.Add(context[fetchFrom + i - 1]);//fetchFrom to fetchFrom+pageSize
                }catch(Exception ex)
                {
                    // MessageBox.Show("ex");
                    continue;
                }
            }
            datagridblotter.ItemsSource = populatedListBlotter;
        }

        private void previousPage_Click(object sender, RoutedEventArgs e)
        {
            pageSlider.Value--;
        }

        private void nextPage_Click(object sender, RoutedEventArgs e)
        {
            pageSlider.Value++;
        }


        private void test(object sender, SelectionChangedEventArgs e)
        {
            Quantitybx.Text = "";
            Yieldtxt.Text = "";
            CPtxt.Text = "";
            DPtxt.Text = "";
            AAtxt.Text = "";
            SAtxt.Text = "";
            //MessageBox.Show(ISINcbx.SelectedItem.ToString());
        }

        private void EnterIsin(object sender, KeyEventArgs e)
        {
            string nwisin;
            if (e.Key == Key.Enter)
            {
                nwisin = ISINcbx.SelectedItem.ToString();

                var user = new WebClient();
                user.Proxy = null;
                string address = IpAddress + "/EBondTraderWeb/rest/bond/tbs?isin=" + nwisin;
                string searchedbond = user.DownloadString(address);
                Stream bondstream = StringToStream(searchedbond);
                StreamReader reader = new StreamReader(bondstream);
                DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(EBond[]));
                EBond[] EbondwithNwIsin = (EBond[])serializer.ReadObject(bondstream);
                EBond NwIsin = EbondwithNwIsin[0];

                FVtxt.Text = NwIsin.faceValue.ToString();
                CRtxt.Text = NwIsin.couponRate.ToString();
                CFtxt.Text = NwIsin.couponFrequency.ToString();
                CrRtxt.Text = NwIsin.creditRating.ToString();
                LPtxt.Text = NwIsin.lastPrice.ToString();
                DateTime mddate = DateTime.Parse(NwIsin.maturityDate);

                MDtxt.Text = mddate.ToShortDateString();
                SDtxt.Text = SettlementDate(NwIsin.settlementDays);

            }
        }
       
        private void ShowDataBlotter(object sender, SelectionChangedEventArgs e)
        {
            var clie = new WebClient();
            clie.Proxy = null;
            // cli.Headers[HttpRequestHeader.ContentType] = "application/json";
            string blotter_populate;
            try {  blotter_populate = clie.DownloadString(IpAddress + "/EBondTraderWeb/rest/bond/blotter/"+accountId.ToString()+"?isin=" + comboBoxfrIsin.SelectedItem.ToString()); }
            catch (Exception ) {
                
               // blotter_populate = clie.DownloadString(IpAddress + "/EBondTraderWeb/rest/bond/blotter/" + accountId.ToString() + "?isin=SQRTT123");
                blotter_populate = clie.DownloadString(IpAddress + "/EBondTraderWeb/rest/bond/blotter/" + accountId.ToString() + "?isin=");
            }

            JavaScriptSerializer js = new JavaScriptSerializer();
            List<Blotter> context = (List<Blotter>)js.Deserialize(blotter_populate, typeof(List<Blotter>));

            datagridblotter.ItemsSource = context;
        }
        List<EBond> compareList = new List<EBond>();

        private void SelectBondDupe(object sender, MouseButtonEventArgs e)
        {
            EBond sentebond = (EBond)dataGridfrbonds.SelectedItem;
            // dataGridfrbonds.ItemsSource= bondsetup(sentebond);
            //dataGridfrbonds.Items.Add(sentebond);
            GridfrBond.IsEnabled = true;
            buttonfrBT.IsEnabled = true;
            Idatetxt.Text = sentebond.issueDate;
            Sdaystxt.Text = sentebond.settlementDays.ToString();
            Fvaltxt.Text = sentebond.faceValue.ToString();
            Hightxt.Text = sentebond.high.ToString();
            Lowtxt.Text = sentebond.low.ToString();
            CiPricetxt.Text = sentebond.changeInPrice.ToString();
        }

        private void numbersOnly(object sender, TextCompositionEventArgs e)
        {

            decimal data;
            e.Handled = !Decimal.TryParse(e.Text, out data);

            if (e.Text.ToString().Equals("."))
            {
                e.Handled = false;
            }



        }

        

        private void logout(object sender, RoutedEventArgs e)
        {
            //DialogResult result =  MessageBoxButton.YesNo("Confirm Logout?");
            // if (result == DialogResult.YES) { Close(); }
            Close();
            MessageBox.Show("You have been Logged Out!!");
        }
        //for yield codes
        public double func(double couponRate, double cleanPrice, double faceValue, double couponFrequency, int time)
         { 
             double couponPayment = (faceValue * couponRate) / 100; 
             return faceValue - (cleanPrice* (Math.Pow(1 + (couponRate / couponFrequency), (couponFrequency* time))) + ((couponPayment* couponFrequency) / couponRate) * (Math.Pow(1 + (couponRate / couponFrequency), (couponFrequency* time)) - 1)); 
         } 
 
 
         public double funcder(double couponRate, double cleanPrice, double faceValue, double couponFrequency, int time)
         { 
            double couponPayment = (faceValue * couponRate) / 100; 
            return -(cleanPrice* time * Math.Pow(1 + (couponRate / couponFrequency), (couponFrequency* time) - 1)) - (couponPayment* couponRate * Math.Pow(1 + (couponRate / couponFrequency), (couponFrequency* time - 1))) / (Math.Pow(couponRate, 2)) + (couponPayment* couponRate) / (Math.Pow(couponRate, 2)); 
 
         } 
 
 
         public double calculateYield(double couponRate, double cleanPrice, double faceValue, double couponFrequency, int time)
         { 
             double couponPayment = (faceValue * couponRate) / 100;


            DateTime issdDate = DateTime.Parse(MDtxt.Text);
 
             double temp, yield; 
             double limit = 0.0001; 
             double deltaY = 0.1; 
             while (deltaY > limit) 
             { 
                 temp = couponRate - (func(couponRate, double.Parse(CPtxt.Text),double.Parse(FVtxt.Text),double.Parse(GetFreq().ToString()), calcdatediff2(issdDate)) / funcder(couponRate, double.Parse(CPtxt.Text), double.Parse(FVtxt.Text), double.Parse(GetFreq().ToString()), calcdatediff2(issdDate))); 
                 deltaY = couponRate - temp; 
                 couponRate = temp; 
                 //MessageBox.Show(deltaY.ToString() + "," + couponRate.ToString()); 
             } 
             yield = couponRate* 100; 
 
 
            return yield; 
        }

        private void calcYield(object sender, KeyEventArgs e)
        {
            EBond it = (EBond)ISINcbx.SelectedItem;
            DateTime issdDate = DateTime.Parse(it.maturityDate);
            
            if (e.Key == Key.Enter)
            {
                double yieldd = calculateYield(double.Parse(CRtxt.Text), double.Parse(CPtxt.Text), double.Parse(FVtxt.Text), double.Parse(GetFreq().ToString()), calcdatediff2(issdDate.Date));
                Yieldtxt.Text = yieldd.ToString();
                AAtxt.Text = CalculateAcrdAmnt().ToString();
                SAtxt.Text = CalculateSetlmntAmnt(it.lastPrice, int.Parse(Quantitybx.Text)).ToString();
                
                 DPtxt.Text = CalDPfrmCP().ToString();
               // DPtxt.Text = (CalculateAcrdAmnt() + Double.Parse(CPtxt.Text)).ToString();
            }
            
        }
        public double CalDPfrmCP()
        {
            double cp = double.Parse(CPtxt.Text);
           return CalculateAcrdAmnt() + cp;
        }
        public int calcdatediff2(DateTime issdDate)
        {
            int diff = (issdDate - DateTime.Today.Date).Days;

            return (diff % 365);

        }

        private void caleverything(object sender, KeyEventArgs e)
        {
            EBond it = (EBond)ISINcbx.SelectedItem;
            DateTime issdDate = DateTime.Parse(it.maturityDate);
            if (e.Key == Key.Enter)
            {
                AAtxt.Text = CalculateAcrdAmnt().ToString();
                CPtxt.Text = (double.Parse(DPtxt.Text) - CalculateAcrdAmnt()).ToString();
                double yieldd = calculateYield(double.Parse(CRtxt.Text), double.Parse(CPtxt.Text), double.Parse(FVtxt.Text), double.Parse(GetFreq().ToString()), calcdatediff2(issdDate.Date));
                Yieldtxt.Text = yieldd.ToString();
                SAtxt.Text = CalculateSetlmntAmnt(it.lastPrice, int.Parse(Quantitybx.Text)).ToString();
            }
        }
    }
}
