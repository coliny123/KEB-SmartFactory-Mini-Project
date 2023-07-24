import requests
import tkinter as tk
import urllib.request
from bs4 import BeautifulSoup
import json
from concurrent.futures import ThreadPoolExecutor
import matplotlib.pyplot as plt
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

executor = ThreadPoolExecutor()

URL = "http://172.30.1.89:80"
button_status = False
temperature_data = []


# ---------------------        functions        --------------------------
def external_conditions():
    # In this function, we get the weather conditions from the API.
    api = 'http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=2817755000'
    urls = urllib.request.urlopen(api).read()
    soup = BeautifulSoup(urls, "html.parser")
    tmin = soup.find_all("tmn")  # lowest temperature
    tmax = soup.find_all("tmx")  # highest temperature
    yyyymmdd = soup.find("tm")  # yyyy-mm-dd 00:00

    incheon_temperature_text = f'City : Incheon, Min temp: {tmin[2].string}C, ' \
                                f'Max temp: {tmax[2].string}C\n' \
                                f'Today : {yyyymmdd.string[0:4]}-{yyyymmdd.string[4:6]}-{yyyymmdd.string[6:8]}'
    lbl_incheon_temperature.config(text=incheon_temperature_text)


def update_current_condition():
    if button_status: # Only send request if button_status is True
        try:
            global temperature_data
            web_server_url = URL + "/data"
            response = requests.get(web_server_url)
            data = response.text

            response_dict = json.loads(data)
            lbl_temperature.config(text=f"Temperature : {response_dict.get('temperature')} C")
            lbl_brightness.config(text=f"Brightness : {response_dict.get('brightness')} LUX")
            lbl_count.config(text=f"Count : {response_dict.get('count')} 개")
            lbl_timestamp.config(text=f"Current Time : {response_dict.get('times')}")

            temperature_data.append(response_dict.get('temperature'))
            if len(temperature_data) > 10:
                temperature_data.pop(0)
        except requests.exceptions.RequestException as e:
            print(f"Error requesting data: {e}")

    # Call external_conditions() regardless of button_status
    external_conditions()


def repeat_update_labels():
    future = executor.submit(update_current_condition)
    future.add_done_callback(lambda x: main.after(1000, repeat_update_labels))

    # Check if graph_frame is visible and update the graph
    if graph_frame.winfo_ismapped():
        update_graph()


def update_graph():
    home_frame.grid_remove()
    graph_frame.grid()
    fig = plot_graph()

    if hasattr(graph_frame, 'canvas'):
        graph_frame.canvas.get_tk_widget().destroy()

    graph_frame.canvas = FigureCanvasTkAgg(fig, master=graph_frame)
    graph_frame.canvas.draw()
    graph_frame.canvas.get_tk_widget().grid(row=0, column=0)


def show_main_frame():
    condition_frame.grid_remove()
    graph_frame.grid_remove()
    home_frame.grid()


def toggle_working_status():
    global button_status
    button_status = not button_status
    if button_status:
        btn_run_stop.config(text="Stop", bg="red")
    else:
        btn_run_stop.config(text="Run", bg="green")


def plot_graph():
    fig = plt.Figure(figsize=(3, 2), dpi=100)
    ax = fig.add_subplot(111)
    ax.plot(temperature_data, marker='o')
    ax.set_title('Temperature')

    ax.set_xticks(range(11), range(11))
    return fig


# ----------------------       get request      -----------------------------
response = requests.get(URL)
data = response.text
response_dict = json.loads(data)
ip_address = response_dict.get('ip_address', '')


# ----------------------       tk & frames      -----------------------------
main = tk.Tk()
main.title("Smart Factory Controller")
main.geometry("350x250")

home_frame = tk.Frame(main)
home_frame.grid(row=0, column=0, sticky='nsew')

condition_frame = tk.Frame(main)
condition_frame.grid(row=0, column=0, sticky='nsew')
condition_frame.grid_remove()

graph_frame = tk.Frame(main)
graph_frame.grid(row=0, column=0, sticky='nsew')
graph_frame.grid_remove()


# -----------       label & button connect with frames        ---------------
lbl_ip = tk.Label(home_frame, text=f"Your IP address: {ip_address}")
lbl_temperature = tk.Label(condition_frame, text="")
lbl_brightness = tk.Label(condition_frame, text="")
lbl_timestamp = tk.Label(condition_frame, text="")
lbl_count = tk.Label(condition_frame, text="")
lbl_incheon_temperature = tk.Label(condition_frame, text="city/min/max")

btn_check_current_condition = tk.Button(home_frame, text="check Current Condition", command=lambda: (home_frame.grid_remove(), condition_frame.grid()))
btn_go_back1 = tk.Button(condition_frame, text="Go Back", command=show_main_frame)
btn_run_stop = tk.Button(condition_frame, text="Run", bg="green", command=toggle_working_status)

btn_graph = tk.Button(home_frame, text="graph", command=update_graph)
btn_go_back2 = tk.Button(graph_frame, text="Go Back", command=show_main_frame)

lbl_ip.pack(padx=10, pady=10)
btn_check_current_condition.pack(padx=10, pady=10)
btn_graph.pack(padx=10, pady=10)

lbl_temperature.grid(row=0, column=0)
lbl_brightness.grid(row=0, column=1)
lbl_count.grid(row=0,column=2)
lbl_timestamp.grid(row=1, column=0, columnspan=3, sticky=tk.EW)
lbl_incheon_temperature.grid(row=2, column=0, columnspan=3, sticky=tk.EW)
btn_go_back1.grid(row=4, column=0, columnspan=3, sticky=tk.EW)
btn_run_stop.grid(row=5, column=0, columnspan=3, sticky=tk.EW)

btn_go_back2.grid(row=4, column=0)


# -----------       condition_frame center aligned      --------------------
for col in range(3):
    condition_frame.grid_columnconfigure(col, weight=1, uniform="col")
for row in range(6):
    condition_frame.grid_rowconfigure(row, weight=1, uniform="row")


# ------------------------    repeat mainloop       ------------------------
repeat_update_labels()
main.mainloop()
