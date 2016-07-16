using System;
using System.Collections.Generic;
using System.Linq;
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
    }
}
