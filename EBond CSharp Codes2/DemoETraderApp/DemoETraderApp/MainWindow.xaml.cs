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
using System.Runtime.Serialization;
using System.Runtime.Serialization.Json;
using System.IO;
using static System.Windows.Documents.List;
using System.Web.Script.Serialization;

namespace DemoETraderApp
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    /// 

    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void ShowBSM(object sender, RoutedEventArgs e)
        {
            // Show();
        }

        private void ListSetup(object sender, RoutedEventArgs e)
        {
            Bondslstbx.Items.Add("AB Stock");
            Bondslstbx.Items.Add("AE Stock");
            Bondslstbx.Items.Add("AD Stock");
            Bondslstbx.Items.Add("AC Stock");
        }
      
        private List<BondClass> bondsetup()
        {
          List<BondClass> BondList = new List<BondClass>();
            BondClass ABStockObj = new BondClass(1, "Isin1", DateTime.Today, "status", 100, 105, 102, "profit", 5);
            BondList.Add(ABStockObj);
            return BondList;
        }
        private void selectbond(object sender, RoutedEventArgs e)
        {


            if (Bondslstbx.SelectedIndex == 0)
            {

                dataGrid.ItemsSource = bondsetup();
            }
            buttonfrBT.IsEnabled = true;
        }

        private void OpenTBWTab(object sender, RoutedEventArgs e)
        {

            tabControl.SelectedItem = TBWTab;
            // Load events...(later..)


        }

        private void Search(object sender, RoutedEventArgs e)
        {

        }


        

        /*      private List<Blotter> blotter_setup()
              {
                  List<Blotter> blotterList = new List<Blotter>();

                  Blotter b = new Blotter(1, "UPB042", 'B', DateTime.Today, 100, 1, "INR", 1.995, 'S', DateTime.Today, 1.999);
                  blotterList.Add(b);
                  return blotterList;

              }*/
        public string response;

        public object BondList { get; private set; }
        public List Bond { get; private set; }
        public object JsonConvert { get; private set; }

        private void blotter_setup(object sender, RoutedEventArgs e)
        {
            //    dataGrid_blotter.ItemsSource = blotter_setup();
               
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

        private void Search_TB_TextChanged(object sender, TextChangedEventArgs e)
        {

        }

        private void textBox_blotter_TextChanged(object sender, TextChangedEventArgs e)
        {
      //    List <Bond> = BondList.Item.StartWith(x => x.isin == textBox_blotter.Text);
        }

        private void datagridblotter_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {

        }

        private void Populate(object sender, RoutedEventArgs e)
        {
            var clie = new WebClient();
           // cli.Headers[HttpRequestHeader.ContentType] = "application/json";
            string blotter_populate = clie.DownloadString("http://192.168.137.37:8080/EBondTraderWeb/rest/bond/blotter");
            
            JavaScriptSerializer js = new JavaScriptSerializer();
            List<Blotter> context = (List<Blotter>)js.Deserialize(blotter_populate, typeof(List<Blotter>));
            /*Stream blotterlist = StringToStream(blotter_populate);
            StreamReader reader = new StreamReader(blotterlist);
            DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(Blotter[]));
            EBond[] context = (EBond[])serializer.ReadObject(EBond);*/

            //           datagridblotter.ItemsSource = context.BuySell;


            //json serializer
            //            DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(Blotter[]));
            //           Blotter[] bookedbondlist = (Blotter[])serializer.ReadObject(blotterlist);

            datagridblotter.ItemsSource = context;
        }
    }
}

